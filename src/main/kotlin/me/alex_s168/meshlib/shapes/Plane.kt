package me.alex_s168.meshlib.shapes

import me.alex_s168.math.vec.impl.Vec3f
import me.alex_s168.meshlib.Triangle
import me.alex_s168.meshlib.TriangleListProvider

/**
 * A simple plane defined by 4 points.
 */
data class Plane (
    val a: Vec3f,
    val b: Vec3f,
    val c: Vec3f,
    val d: Vec3f
): TriangleListProvider {

    private val tris = listOf(
        Triangle(Vec3f(), a, b, c),
        Triangle(Vec3f(), c, d, a)
    )

    override fun get(): List<Triangle>
        = tris

}