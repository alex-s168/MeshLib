package me.alex_s168.meshlib

import me.alex_s168.math.AABB
import me.alex_s168.math.HitResult
import me.alex_s168.math.Ray
import me.alex_s168.math.Vec3
import me.alex_s168.meshlib.texture.TextureFace

/**
 * A mesh is a collection of triangles.
 * It is used to represent a 3D object.
 * This class is also a triangle list provider for the [TriangleListProvider] interface as well as a collection of [Face].
 */
data class Mesh (

    /**
     * The triangles of the mesh.
     */
    private val triangleList: MutableList<Triangle>,

    /**
     * The texture coordinates of the mesh.
     * This list always needs to be the same size as the triangles list!
     */
    private val textureCoordinates: MutableList<TextureFace?> = mutableListOf()

): TriangleListProvider, MutableList<Face> {

    /**
     * Creates a new mesh with the specified triangles.
     */
    constructor(faces: Iterable<Face>): this(
        faces.mapTo(mutableListOf()) { it.tri },
        faces.mapTo(mutableListOf()) { it.tex }
    )

    init {
        if (triangleList.size > textureCoordinates.size) {
            repeat(triangleList.size - textureCoordinates.size) {
                textureCoordinates += TextureFace()
            }
        }
    }

    /**
     * The amount of triangles in this mesh.
     */
    override val size: Int
        get() = triangleList.size

    /**
     * Removes all duplicate faces from the mesh.
     */
    fun removeDuplicates() {
        val newTriangles = mutableListOf<Triangle>()
        val newTextureCoordinates = mutableListOf<TextureFace?>()

        for (i in triangleList.indices) {
            val tri = triangleList[i]
            val tex = textureCoordinates[i]

            if (tri in newTriangles) continue

            newTriangles += tri
            newTextureCoordinates += tex
        }

        triangleList.clear()
        textureCoordinates.clear()

        triangleList += newTriangles
        textureCoordinates += newTextureCoordinates
    }

    /**
     * Updates all cached values.
     */
    fun update() {
        updateAABB()
    }

    /**
     * Removes all triangles from the mesh.
     */
    override fun clear() {
        triangleList.clear()
        textureCoordinates.clear()
        changed()
        updateAABB()
    }

    /**
     * Add all faces from the specified collection to the mesh.
     */
    override fun addAll(elements: Collection<Face>): Boolean {
        elements.forEach {
            triangleList += it.tri
            textureCoordinates += it.tex
        }
        changed()
        return true
    }

    /**
     * Inserts all faces from the specified collection into the mesh at the specified index.
     */
    override fun addAll(index: Int, elements: Collection<Face>): Boolean {
        triangleList.addAll(index, elements.map { it.tri })
        textureCoordinates.addAll(index, elements.map { it.tex })
        changed()
        return true
    }

    /**
     * Insert a face into the mesh at the specified index.
     */
    override fun add(index: Int, element: Face) {
        triangleList.add(index, element.tri)
        textureCoordinates.add(index, element.tex)
        changed()
    }

    /**
     * Adds a face to the mesh.
     */
    override fun add(element: Face): Boolean {
        triangleList += element.tri
        textureCoordinates += element.tex
        changed()
        return true
    }

    /**
     * Returns the face at the specified index.
     */
    override fun get(index: Int): Face {
        check()
        return Face(triangleList[index], textureCoordinates[index])
    }

    /**
     * Returns the index of the specified face.
     */
    override fun indexOf(element: Face): Int {
        check()
        val i = triangleList.indexOf(element.tri)
        if (textureCoordinates[i] != element.tex) return -1
        return i
    }

    /**
     * Checks if the mesh is empty.
     */
    override fun isEmpty(): Boolean {
        check()
        return triangleList.isEmpty()
    }

    /**
     * Checks if the mesh contains the specified face.
     */
    override fun containsAll(elements: Collection<Face>): Boolean {
        for (face in elements) {
            if (face !in this) return false
        }
        return true
    }

    /**
     * Checks if the mesh contains the specified face.
     */
    override fun contains(element: Face): Boolean =
        indexOf(element) != -1

    /**
     * The triangles in this mesh.
     * The returned list is NOT a copy of the internal list.
     * Adding or removing triangles from the returned list will also add or remove them from the mesh.
     * IF you do that, you need to make sure that the texture coordinates list always has the same size as the triangles list!
     */
    val triangles: Iterable<Triangle>
        get() = triangleList

    private fun check() {
        if (triangleList.size != textureCoordinates.size) {
            throw IllegalStateException("The amount of triangles and texture coordinates is not the same! Was it modified externally?")
        }
    }

    /**
     * Returns the triangles of the mesh.
     */
    override fun get(): List<Triangle> =
        triangleList

    private var changed = true

    /**
     * The axis aligned bounding box of the mesh.
     * Accessing this will return the cached AABB.
     */
    var aabbUnsafe = AABB(Vec3(), Vec3())

    /**
     * The axis aligned bounding box of the mesh.
     * Accessing this will update the AABB if it is not up-to-date.
     */
    val aabb: AABB
        get() {
            if (!changed) return aabbUnsafe
            updateAABB()
            return aabbUnsafe
        }

    /**
     * Updates the AABB of the mesh.
     * This method should be called after the mesh has been changed.
     */
    private fun updateAABB() {
        if (!changed) return
        changed = false
        aabbUnsafe = triangleList.aabb()
    }

    /**
     * Marks the mesh as changed.
     */
    fun changed() {
        changed = true
    }

    /**
     * The corners of the mesh.
     * Accessing this is slow.
     */
    val corners: Iterable<Vec3>
        get() {
            val c = mutableSetOf<Vec3>()
            for (tri in triangleList) {
                c += tri.a
                c += tri.b
                c += tri.c
            }
            return c
        }

    /**
     * Checks if a point is inside the mesh.
     * @param point The point to check.
     * @return True if the point is inside the mesh, false otherwise.
     */
    fun isInside(point: Vec3): Boolean {
        if (!(point inside aabbUnsafe)) return false

        for (triangle in triangleList) {
            if (point inside triangle) return true
        }

        return false
    }

    /**
     * Adds a triangle without checking if it is inside the mesh.
     * @param tri The triangle to add.
     */
    fun add(tri: Triangle) {
        if (tri inside this) {
            // skip triangle because it is inside the mesh
            return
        }

        triangleList += tri
        textureCoordinates += TextureFace()

        changed()
    }

    /**
     * Adds triangles WITHOUT checking if they are inside the mesh.
     * @param tris The triangles to add.
     */
    fun fastAdd(tris: TriangleListProvider) {
        tris.get().forEach {
            triangleList.add(it)
            textureCoordinates += TextureFace()
        }
    }

    /**
     * Adds triangles only if they are not inside the mesh.
     * @param tris The triangles to add.
     */
    fun add(tris: TriangleListProvider) {
        for (tri in tris.get()) {
            if (tri inside this) {
                // skip triangle because it already is inside the mesh
                continue
            }

            triangleList += tri
            textureCoordinates += TextureFace()
        }

        changed()
    }

    /**
     * Ray casts the mesh and returns the closest hit point.
     * @param ray The ray to cast.
     * @return The hit point and normal in the form as a HitResult.
     */
    fun rayCast(ray: Ray): HitResult {
        var closestHit: Float? = null
        var hitTriangle: Triangle? = null

        for (triangle in triangleList) {
            val t = triangle.rayCast(ray)
            if (t != null && (closestHit == null || t < closestHit)) {
                closestHit = t
                hitTriangle = triangle
            }
        }

        if (closestHit != null) {
            val hitPoint = ray.origin + ray.direction * closestHit
            // Calculate the triangle's normal
            val normal = (hitTriangle!!.b - hitTriangle.a)
                .cross(hitTriangle.c - hitTriangle.a)
                .normalize()
            return HitResult(hitPoint, normal)
        }

        return HitResult(null, null) // No intersection with any triangle in the mesh.
    }

    private class MeshListIterator(
        var it: Int = 0,
        val mesh: Mesh
    ): MutableListIterator<Face> {
        override fun add(element: Face) {
            mesh.add(it, element)
            it += 1
        }

        override fun hasNext(): Boolean =
            it < mesh.size - 1

        override fun hasPrevious(): Boolean =
            it > 0

        override fun next(): Face =
            mesh[it++]

        override fun nextIndex(): Int =
            it

        override fun previous(): Face =
            mesh[--it]

        override fun previousIndex(): Int =
            it - 1

        override fun remove() {
            mesh.removeAt(previousIndex())
        }

        override fun set(element: Face) {
            mesh[previousIndex()] = element
        }
    }

    /**
     * Returns an iterator over the faces in the mesh.
     */
    override fun iterator(): MutableIterator<Face> =
        MeshListIterator(0, this)

    /**
     * Returns a list iterator over the faces in the mesh.
     */
    override fun listIterator(): MutableListIterator<Face> =
        MeshListIterator(0, this)

    /**
     * Returns a list iterator over the faces in the mesh.
     */
    override fun listIterator(index: Int): MutableListIterator<Face> =
        MeshListIterator(index, this)

    /**
     * Removes the face at the specified index.
     */
    override fun removeAt(index: Int): Face {
        val tri = triangleList.removeAt(index)
        val tex = textureCoordinates.removeAt(index)
        changed()
        return Face(tri, tex)
    }

    /**
     * Sets the face at the specified index.
     */
    override fun set(index: Int, element: Face): Face {
        val tri = triangleList.set(index, element.tri)
        val tex = textureCoordinates.set(index, element.tex)
        changed()
        return Face(tri, tex)
    }

    /**
     * Unsupported.
     */
    override fun retainAll(elements: Collection<Face>): Boolean =
        throw UnsupportedOperationException("retainAll is not supported in meshes!")

    /**
     * Removes all faces from the mesh.
     */
    override fun removeAll(elements: Collection<Face>): Boolean {
        var changed = false
        for (face in elements) {
            if (face in this) {
                remove(face)
                changed = true
            }
        }
        return changed
    }

    /**
     * Removes the face at the specified index.
     */
    override fun remove(element: Face): Boolean {
        val i = indexOf(element)
        if (i == -1) return false
        triangleList.removeAt(i)
        textureCoordinates.removeAt(i)
        changed()
        return true
    }

    /**
     * Not supported.
     */
    override fun subList(fromIndex: Int, toIndex: Int): MutableList<Face> {
        throw UnsupportedOperationException("subList is not supported in meshes!")
    }

    /**
     * Not really supported.
     */
    override fun lastIndexOf(element: Face): Int =
        indexOf(element)

}