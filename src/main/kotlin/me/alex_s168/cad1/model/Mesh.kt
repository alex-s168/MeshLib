package me.alex_s168.cad1.model

import me.alex_s168.cad1.math.AABB
import me.alex_s168.cad1.math.HitResult
import me.alex_s168.cad1.math.Ray
import me.alex_s168.cad1.math.Vec3

/**
 * A mesh is a collection of triangles.
 * It is used to represent a 3D object.
 */
data class Mesh (
    /**
     * The triangles of the mesh.
     */
    val triangles: MutableList<Triangle>
): TriangleListProvider {

    /**
     * returns the triangles of the mesh.
     */
    override fun get(): List<Triangle> =
        triangles

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
    fun updateAABB() {
        if (!changed) return
        changed = false
        aabbUnsafe = triangles.aabb()
    }

    /**
     * Marks the mesh as changed.
     */
    fun changed() {
        changed = true
    }

    /**
     * Checks if a point is inside the mesh.
     * @param point The point to check.
     * @return True if the point is inside the mesh, false otherwise.
     */
    fun isInside(point: Vec3): Boolean {
        if (!(point inside aabbUnsafe)) return false

        for (triangle in triangles) {
            if (point inside triangle) return true
        }

        return false
    }

    /**
     * Subtracts a mesh from this mesh.
     * @param meshB The mesh to subtract.
     */
    fun subtractMesh(meshB: Mesh) {
        TODO("not implemented yet")
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

        triangles.add(tri)

        changed()
    }

    /**
     * Adds triangles WITHOUT checking if they are inside the mesh.
     * @param tris The triangles to add.
     */
    fun fastAdd(tris: TriangleListProvider) {
        tris.get().forEach {
            triangles.add(it)
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

            triangles += tri
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

        for (triangle in triangles) {
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

}