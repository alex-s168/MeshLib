package me.alex_s168.cad1.model.shapes

import me.alex_s168.cad1.math.Vec3
import me.alex_s168.cad1.model.Triangle
import me.alex_s168.cad1.model.TriangleListProvider

/**
 * A cube is defined by 8 points.
 */
open class Cube (
    val a: Vec3,
    val b: Vec3,
    val c: Vec3,
    val d: Vec3,
    val e: Vec3,
    val f: Vec3,
    val g: Vec3,
    val h: Vec3
): TriangleListProvider {

    // TODO: normals
    private val tris = listOf(
        Triangle(Vec3(), e, g, h),
        Triangle(Vec3(), e, f, g),

        Triangle(Vec3(), a, b, c),
        Triangle(Vec3(), a, c, d),

        Triangle(Vec3(), b, f, g),
        Triangle(Vec3(), b, c, g),

        Triangle(Vec3(), c, d, h),
        Triangle(Vec3(), c, g, h),

        Triangle(Vec3(), a, b, e),
        Triangle(Vec3(), b, e, f),

        Triangle(Vec3(), a, e, h),
        Triangle(Vec3(), a, d, h)
    )

    override fun get(): List<Triangle> =
        tris

}