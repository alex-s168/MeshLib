package model.shapes

import math.Vec3
import model.Triangle
import model.TriangleListProvider

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