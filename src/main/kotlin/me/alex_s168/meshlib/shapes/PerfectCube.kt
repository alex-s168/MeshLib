package me.alex_s168.meshlib.shapes

import me.alex_s168.math.Vec3

/**
 * A cube with all sides of equal length.
 */
data class PerfectCube(
    val mid: Vec3,
    val size: Float
): Cube(
    Vec3(mid.x - size / 2, mid.y - size / 2, mid.z - size / 2),
    Vec3(mid.x + size / 2, mid.y - size / 2, mid.z - size / 2),
    Vec3(mid.x + size / 2, mid.y + size / 2, mid.z - size / 2),
    Vec3(mid.x - size / 2, mid.y + size / 2, mid.z - size / 2),
    Vec3(mid.x - size / 2, mid.y - size / 2, mid.z + size / 2),
    Vec3(mid.x + size / 2, mid.y - size / 2, mid.z + size / 2),
    Vec3(mid.x + size / 2, mid.y + size / 2, mid.z + size / 2),
    Vec3(mid.x - size / 2, mid.y + size / 2, mid.z + size / 2)
)