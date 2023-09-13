package model.shapes

import math.Vec3

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