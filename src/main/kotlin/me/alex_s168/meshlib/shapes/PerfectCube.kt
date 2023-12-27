package me.alex_s168.meshlib.shapes

import me.alex_s168.math.vec.impl.Vec3f

/**
 * A cube with all sides of equal length.
 */
data class PerfectCube(
    val mid: Vec3f,
    val size: Float
): Cube(
    Vec3f(mid.x - size / 2, mid.y - size / 2, mid.z - size / 2),
    Vec3f(mid.x + size / 2, mid.y - size / 2, mid.z - size / 2),
    Vec3f(mid.x + size / 2, mid.y + size / 2, mid.z - size / 2),
    Vec3f(mid.x - size / 2, mid.y + size / 2, mid.z - size / 2),
    Vec3f(mid.x - size / 2, mid.y - size / 2, mid.z + size / 2),
    Vec3f(mid.x + size / 2, mid.y - size / 2, mid.z + size / 2),
    Vec3f(mid.x + size / 2, mid.y + size / 2, mid.z + size / 2),
    Vec3f(mid.x - size / 2, mid.y + size / 2, mid.z + size / 2)
)