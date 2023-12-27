package me.alex_s168.meshlib

import me.alex_s168.math.AABB3f
import me.alex_s168.math.vec.impl.Vec3f

/**
 * Returns the AABB of the given list of triangles.
 * Does not cache the result!
 * @return the AABB of the given list of triangles.
 */
fun List<Triangle>.aabb() =
    map { it.bb }
        .reduceOrNull { a, b -> a with b }
        ?: AABB3f(Vec3f(), Vec3f())

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