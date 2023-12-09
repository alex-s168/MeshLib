package me.alex_s168.meshlib.texture

import me.alex_s168.math.Vec3

/**
 * A texture face
 */
data class TextureFace(
    val a: TextureCoordinate = TextureCoordinate(0f),
    val b: TextureCoordinate = TextureCoordinate(0f),
    val c: TextureCoordinate = TextureCoordinate(0f)
) {
    fun getAt(point: Vec3): Triple<Float, Float, Float> {
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

    operator fun times(other: Vec3): TextureFace =
        TextureFace(
            a * other,
            b * other,
            c * other
        )

    operator fun plus(other: TextureFace): TextureFace =
        TextureFace(
            a + other.a,
            b + other.b,
            c + other.c
        )

    operator fun minus(other: TextureFace): TextureFace =
        TextureFace(
            a - other.a,
            b - other.b,
            c - other.c
        )

    operator fun div(other: Float): TextureFace =
        TextureFace(
            a / other,
            b / other,
            c / other
        )
}