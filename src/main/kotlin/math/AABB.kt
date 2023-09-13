package math

data class AABB (
    val min: Vec3,
    val max: Vec3
) {

    fun isInside(point: Vec3): Boolean =
        point.x in min.x..max.x &&
        point.y in min.y..max.y &&
        point.z in min.z..max.z

    infix fun with(other: AABB): AABB {
        val min = Vec3(
            minOf(min.x, other.min.x),
            minOf(min.y, other.min.y),
            minOf(min.z, other.min.z)
        )
        val max = Vec3(
            maxOf(max.x, other.max.x),
            maxOf(max.y, other.max.y),
            maxOf(max.z, other.max.z)
        )
        return AABB(min, max)
    }

}