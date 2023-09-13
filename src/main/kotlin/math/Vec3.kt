package math

import model.Triangle
import java.nio.ByteBuffer

data class Vec3 (
    val x: Float = 0f,
    val y: Float = 0f,
    val z: Float = 0f
): VecLike<Vec3> {

    fun save(buf: ByteBuffer) {
        buf.putFloat(x)
        buf.putFloat(y)
        buf.putFloat(z)
    }

    companion object {

        fun from(buf: ByteBuffer): Vec3 =
            Vec3(
                buf.getFloat(),
                buf.getFloat(),
                buf.getFloat()
            )

    }

    override operator fun plus(other: Vec3): Vec3 =
        Vec3(x + other.x, y + other.y, z + other.z)


    override operator fun minus(other: Vec3): Vec3 =
        Vec3(x - other.x, y - other.y, z - other.z)


    override operator fun times(other: Vec3): Vec3 =
        Vec3(x * other.x, y * other.y, z * other.z)

    override operator fun times(other: Int): Vec3 =
        Vec3(x * other, y * other, z * other)

    override operator fun times(other: Float): Vec3 =
        Vec3(x * other, y * other, z * other)


    override operator fun div(other: Vec3): Vec3 =
        Vec3(x / other.x, y / other.y, z / other.z)

    override fun div(other: Int): Vec3 =
        Vec3(x / other, y / other, z / other)

    override fun div(other: Float): Vec3 =
        Vec3(x / other, y / other, z / other)


    override fun cross(other: Vec3): Vec3 =
        Vec3(
            y * other.z - z * other.y,
            z * other.x - x * other.z,
            x * other.y - y * other.x
        )


    override fun dot(other: Vec3): Float =
        x * other.x + y * other.y + z * other.z


    override fun length(): Float =
        kotlin.math.sqrt(x * x + y * y + z * z)


    override fun normalize(): Vec3 {
        val len = length()
        return Vec3(x / len, y / len, z / len)
    }

    infix fun inside(aabb: AABB): Boolean =
        aabb.isInside(this)

    infix fun inside(triangle: Triangle): Boolean {
        val EPSILON = 1e-8f

        val edge1 = triangle.b - triangle.a
        val edge2 = triangle.c - triangle.a

        val h = this.cross(edge2)
        val va = edge1.dot(h)

        if (kotlin.math.abs(va) < EPSILON) {
            return false // Ray is parallel to the triangle.
        }

        val f = 1.0f / va
        val s = this - triangle.a
        val u = f * s.dot(h)

        if (u < 0.0f || u > 1.0f) {
            return false // Intersection is outside the triangle.
        }

        val q = s.cross(edge1)
        val v = f * this.dot(q)

        if (v < 0.0f || u + v > 1.0f) {
            return false // Intersection is outside the triangle.
        }

        // At this stage we can compute t to find out where the intersection point is on the line.
        val t = f * edge2.dot(q)

        return t > EPSILON // Ray intersection.
    }

}