package model

import math.AABB
import math.HitResult
import math.Ray
import math.Vec3
import java.lang.AssertionError


data class Mesh (
    val triangles: MutableList<Triangle>
): TriangleListProvider {

    override fun get(): List<Triangle> =
        triangles

    private var changed = true
    var aabbUnsafe = AABB(Vec3(), Vec3())

    val aabb: AABB
        get() {
            if (!changed) return aabbUnsafe
            updateAABB()
            return aabbUnsafe
        }

    fun updateAABB() {
        changed = false
        aabbUnsafe = triangles.aabb()
    }

    fun changed() {
        changed = true
    }

    fun isInside(point: Vec3): Boolean {
        if (!(point inside aabbUnsafe)) return false

        for (triangle in triangles) {
            if (point inside triangle) return true
        }

        return false
    }

    fun subtractMesh(meshB: Mesh) {
        TODO("not implemented yet")
    }

    fun add(tri: Triangle) {
        if (tri inside this) {
            // skip triangle because it is inside the mesh
            return
        }

        triangles.add(tri)

        changed()
    }

    fun add(tris: TriangleListProvider) {
        for (tri in tris.get()) {
            if (tri inside this) {
                // skip triangle because it already is inside the mesh
                continue
            }

            triangles.addAll(optimize(tri))
        }

        changed()
    }

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

    fun optimize(tri: Triangle): List<Triangle> {
        val EPSILON = 1e-8f

        val edge1 = tri.b - tri.a
        val edge2 = tri.c - tri.a

        val h = edge1.cross(edge2)
        val va = edge1.dot(h)

        if (kotlin.math.abs(va) < EPSILON) {
            return listOf(tri) // Triangle is already flat.
        }

        val f = 1.0f / va

        val s = tri.a - tri.a
        val u = f * s.dot(h)

        if (u < 0.0f || u > 1.0f) {
            return listOf(tri) // Intersection is outside the triangle.
        }

        val q = s.cross(edge1)
        val v = f * tri.a.dot(q)

        if (v < 0.0f || u + v > 1.0f) {
            return listOf(tri) // Intersection is outside the triangle.
        }

        // At this stage we can compute t to find out where the intersection point is on the line.
        val t = f * edge2.dot(q)

        if (t < EPSILON) {
            return listOf(tri) // Triangle is already flat.
        }

        val hitPoint = tri.a + edge1 * u + edge2 * v
        val hitPoint2 = tri.a + edge1 * u + edge2 * v + h * t

        val normal = (tri.b - tri.a)
            .cross(tri.c - tri.a)
            .normalize()

        val normal2 = (tri.b - tri.a)
            .cross(tri.c - tri.a)
            .normalize()

        val tri1 = Triangle(normal, tri.a, tri.b, hitPoint)
        val tri2 = Triangle(normal2, tri.a, hitPoint, hitPoint2)

        return listOf(tri1, tri2)
    }

}