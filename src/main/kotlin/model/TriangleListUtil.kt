package model

import math.AABB
import math.Vec3

fun List<Triangle>.aabb() = map { it.getAABB() }.reduceOrNull { a, b -> a with b } ?: AABB(Vec3(), Vec3())

fun TriangleListProvider.aabb() =
    get().aabb()

fun List<Triangle>.withoutInvisible(mesh: Mesh): List<Triangle> =
    filter { !(it inside mesh) }