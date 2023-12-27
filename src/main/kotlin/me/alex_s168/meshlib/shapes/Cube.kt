package me.alex_s168.meshlib.shapes

import me.alex_s168.math.vec.impl.Vec3f
import me.alex_s168.meshlib.Triangle
import me.alex_s168.meshlib.TriangleListProvider

/**
 * A cube is defined by 8 points.
 */
open class Cube (
    val a: Vec3f,
    val b: Vec3f,
    val c: Vec3f,
    val d: Vec3f,
    val e: Vec3f,
    val f: Vec3f,
    val g: Vec3f,
    val h: Vec3f
): TriangleListProvider {

    // TODO: normals
    private val tris = listOf(
        Triangle(Vec3f(), e, g, h),
        Triangle(Vec3f(), e, f, g),

        Triangle(Vec3f(), a, b, c),
        Triangle(Vec3f(), a, c, d),

        Triangle(Vec3f(), b, f, g),
        Triangle(Vec3f(), b, c, g),

        Triangle(Vec3f(), c, d, h),
        Triangle(Vec3f(), c, g, h),

        Triangle(Vec3f(), a, b, e),
        Triangle(Vec3f(), b, e, f),

        Triangle(Vec3f(), a, e, h),
        Triangle(Vec3f(), a, d, h)
    )

    override fun get(): List<Triangle> =
        tris

}