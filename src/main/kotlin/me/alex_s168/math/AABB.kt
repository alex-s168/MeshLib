package me.alex_s168.math

/**
 * Axis-aligned bounding box.
 * @property min The minimum point of the box.
 * @property max The maximum point of the box.
 */
data class AABB (
    val min: Vec3,
    val max: Vec3
) {

    /**
     * The height of the box.
     */
    val height: Float get() =
        max.y - min.y

    /**
     * The width of the box.
     */
    val width: Float get() =
        max.x - min.x

    /**
     * The depth of the box.
     */
    val depth: Float get() =
        max.z - min.z

    /**
     * A vector representing the size of the box. (width, height, depth)
     */
    val size: Vec3 get() =
        Vec3(width, height, depth)

    /**
     * Returns a string representation of the box.
     */
    override fun toString(): String =
        "AABB($min, $max)"

    /**
     * Returns true if the given point is inside the box.
     */
    fun isInside(point: Vec3): Boolean =
        point.x in min.x..max.x &&
        point.y in min.y..max.y &&
        point.z in min.z..max.z

    /**
     * Combines this box with another box, returning a new box that contains both.
     */
    infix fun with(other: AABB): AABB =
        AABB(
            min = Vec3(
                minOf(min.x, other.min.x),
                minOf(min.y, other.min.y),
                minOf(min.z, other.min.z)
            ),
            max = Vec3(
                maxOf(max.x, other.max.x),
                maxOf(max.y, other.max.y),
                maxOf(max.z, other.max.z)
            )
        )

}