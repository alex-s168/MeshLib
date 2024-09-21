package me.alex_s168.meshlib

import me.alex_s168.math.AABB3f
import me.alex_s168.math.EnclosedVolume3
import me.alex_s168.math.Ray3f
import me.alex_s168.math.vec.impl.Vec3f

/**
 * Immutable Triangle class.
 * @param normal Normal vector of the triangle.
 * @param a First vertex of the triangle.
 * @param b Second vertex of the triangle.
 * @param c Third vertex of the triangle.
 */
data class Triangle (
    val normal: Vec3f,
    val a: Vec3f,
    val b: Vec3f,
    val c: Vec3f
): EnclosedVolume3 {

    override fun toString() =
        "Triangle([$a, $b, $c], $normal)"

    /**
     * Computes the AABB of the triangle if it has not been computed yet.
     * @return AABB of the triangle.
     */
    override val bb by lazy {
        val minX = kotlin.math.min(a.x, kotlin.math.min(b.x, c.x))
        val minY = kotlin.math.min(a.y, kotlin.math.min(b.y, c.y))
        val minZ = kotlin.math.min(a.z, kotlin.math.min(b.z, c.z))
        val maxX = kotlin.math.max(a.x, kotlin.math.max(b.x, c.x))
        val maxY = kotlin.math.max(a.y, kotlin.math.max(b.y, c.y))
        val maxZ = kotlin.math.max(a.z, kotlin.math.max(b.z, c.z))

        AABB3f(Vec3f(minX, minY, minZ), Vec3f(maxX, maxY, maxZ))
    }

    override fun isInside(point: Vec3f): Boolean =
        isInside(point, 0f)

    override fun isInside(point: Vec3f, tolerance: Float): Boolean =
        bb.isInside(point, tolerance)

    /**
     * Checks if the triangle is inside the given AABB.
     */
    infix fun inside(aabb: AABB3f): Boolean =
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
    fun rayCast(ray: Ray3f): Float? { // chatgpt
        // Find vectors for two edges sharing a
        val edge1 = b - a
        val edge2 = c - a

        // Begin calculating determinant - also used to calculate U parameter
        val pvec = ray.direction.cross(edge2)

        // If determinant is near zero, ray lies in plane of triangle
        val det = edge1.dot(pvec)

        // Return null if the triangle is degenerate (avoid division by zero)
        if (det < 1e-8 && det > -1e-8) return null

        val invDet = 1.0f / det

        // Calculate distance from vertex a to ray origin
        val tvec = ray.origin - a

        // Calculate U parameter and test bounds
        val u = tvec.dot(pvec) * invDet
        if (u < 0.0f || u > 1.0f) return null

        // Prepare to test V parameter
        val qvec = tvec.cross(edge1)

        // Calculate V parameter and test bounds
        val v = ray.direction.dot(qvec) * invDet
        if (v < 0.0f || u + v > 1.0f) return null

        // Calculate t, the distance along the ray to the intersection point
        val t = edge2.dot(qvec) * invDet

        return t
    }

}