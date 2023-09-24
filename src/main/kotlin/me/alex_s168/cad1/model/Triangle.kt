package me.alex_s168.cad1.model

import me.alex_s168.cad1.math.AABB
import me.alex_s168.cad1.math.Ray
import me.alex_s168.cad1.math.Vec3

/**
 * Immutable Triangle class.
 * @param normal Normal vector of the triangle.
 * @param a First vertex of the triangle.
 * @param b Second vertex of the triangle.
 * @param c Third vertex of the triangle.
 */
data class Triangle (
    val normal: Vec3,
    val a: Vec3,
    val b: Vec3,
    val c: Vec3
) {

    private var aabb: AABB? = null

    /**
     * Computes the AABB of the triangle if it has not been computed yet.
     * @return AABB of the triangle.
     */
    fun getAABB(): AABB {
        if (aabb != null) return aabb!!

        val minX = kotlin.math.min(a.x, kotlin.math.min(b.x, c.x))
        val minY = kotlin.math.min(a.y, kotlin.math.min(b.y, c.y))
        val minZ = kotlin.math.min(a.z, kotlin.math.min(b.z, c.z))
        val maxX = kotlin.math.max(a.x, kotlin.math.max(b.x, c.x))
        val maxY = kotlin.math.max(a.y, kotlin.math.max(b.y, c.y))
        val maxZ = kotlin.math.max(a.z, kotlin.math.max(b.z, c.z))

        aabb = AABB(Vec3(minX, minY, minZ), Vec3(maxX, maxY, maxZ))

        return aabb!!
    }

    /**
     * Checks if the triangle is inside the given AABB.
     */
    infix fun inside(aabb: AABB): Boolean =
        aabb.isInside(a) && aabb.isInside(b) && aabb.isInside(c)

    /**
     * Checks if the triangle is inside the given mesh.
     */
    infix fun inside(mesh: Mesh): Boolean =
        mesh.isInside(a) && mesh.isInside(b) && mesh.isInside(c)

    /**
     * rayCast function for triangles.
     * @param ray Ray to check for intersection.
     * @return Distance to the intersection point or null if there is no intersection.
     */
    fun rayCast(ray: Ray): Float? {
        val epsilon = 1e-8f

        val edge1 = b - a
        val edge2 = c - a

        val h = ray.direction.cross(edge2)
        val va = edge1.dot(h)

        if (kotlin.math.abs(va) < epsilon) {
            return null // Ray is parallel to the triangle.
        }

        val f = 1.0f / va
        val s = ray.origin - a
        val u = f * s.dot(h)

        if (u < 0.0f || u > 1.0f) {
            return null // Intersection is outside the triangle.
        }

        val q = s.cross(edge1)
        val v = f * ray.direction.dot(q)

        if (v < 0.0f || u + v > 1.0f) {
            return null // Intersection is outside the triangle.
        }

        val t = f * edge2.dot(q)

        if (t > epsilon) {
            return t // Intersection along the ray direction.
        }

        return null // Intersection is behind the ray origin.
    }

}