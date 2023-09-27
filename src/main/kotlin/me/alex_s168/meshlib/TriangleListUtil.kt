package me.alex_s168.meshlib

import me.alex_s168.math.AABB
import me.alex_s168.math.Vec3

/**
 * Returns the AABB of the given list of triangles.
 * Does not cache the result!
 * @return the AABB of the given list of triangles.
 */
fun List<Triangle>.aabb() =
    map { it.getAABB() }
        .reduceOrNull { a, b -> a with b }
        ?: AABB(Vec3(), Vec3())

/**
 * Returns the AABB of the given list of triangles.
 * Does not cache the result!
 * @return the AABB of the given list of triangles.
 */
fun TriangleListProvider.aabb() =
    get().aabb()

/**
 * Returns a new list of triangles but without triangles that are inside the given mesh.
 */
fun List<Triangle>.withoutInvisible(mesh: Mesh): List<Triangle> =
    filter { !(it inside mesh) }