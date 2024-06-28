package me.alex_s168.meshlib.texture

import me.alex_s168.math.vec.impl.Vec3f

/**
 * A texture face
 */
data class TextureFace(
    val a: TextureCoordinate = TextureCoordinate(),
    val b: TextureCoordinate = TextureCoordinate(),
    val c: TextureCoordinate = TextureCoordinate()
) {
    override fun toString() =
        "TextureFace($a, $b, $c)"

    infix fun at(point: Vec3f): Triple<Float, Float, Float> {
        val v0 = b - a
        val v1 = c - a
        val v2 = point - a
        val d00 = v0.dot(v0)
        val d01 = v0.dot(v1)
        val d11 = v1.dot(v1)
        val d20 = v2.dot(v0)
        val d21 = v2.dot(v1)
        val denom = d00 * d11 - d01 * d01
        val v = (d11 * d20 - d01 * d21) / denom
        val w = (d00 * d21 - d01 * d20) / denom
        val u = 1 - v - w
        return Triple(u, v, w)
    }

    operator fun times(other: Float): TextureFace =
        TextureFace(
            a * other,
            b * other,
            c * other
        )

    operator fun times(other: Vec3f): TextureFace =
        TextureFace(
            a * other,
            b * other,
            c * other
        )

    operator fun div(other: Vec3f): TextureFace =
        TextureFace(
            a / other,
            b / other,
            c / other
        )

    operator fun div(other: Float): TextureFace =
        TextureFace(
            a / other,
            b / other,
            c / other
        )

    operator fun plus(other: Vec3f): TextureFace =
        TextureFace(
            a + other,
            b + other,
            c + other
        )

    operator fun minus(other: Vec3f): TextureFace =
        TextureFace(
            a - other,
            b - other,
            c - other
        )
}