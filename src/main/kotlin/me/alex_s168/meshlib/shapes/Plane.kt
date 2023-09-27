package me.alex_s168.meshlib.shapes

import me.alex_s168.math.Vec3
import me.alex_s168.meshlib.Triangle
import me.alex_s168.meshlib.TriangleListProvider

/**
 * A simple plane defined by 4 points.
 */
data class Plane (
    val a: Vec3,
    val b: Vec3,
    val c: Vec3,
    val d: Vec3
): TriangleListProvider {

    private val tris = listOf(
        Triangle(Vec3(), a, b, c),
        Triangle(Vec3(), c, d, a)
    )

    override fun get(): List<Triangle>
        = tris

}