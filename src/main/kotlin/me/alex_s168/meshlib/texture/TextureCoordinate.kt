package me.alex_s168.meshlib.texture

import me.alex_s168.math.Vec3

/**
 * A texture coordinate
 */
data class TextureCoordinate(
    val u: Float,
    val v: Float = 0f,
    val w: Float = 0f,
): Vec3(u, v, w) {
    override operator fun times(other: Float): TextureCoordinate =
        TextureCoordinate(
            u * other,
            v * other,
            w * other
        )

    override operator fun times(other: Vec3): TextureCoordinate =
        TextureCoordinate(
            u * other.x,
            v * other.y,
            w * other.z
        )

    override operator fun plus(other: Vec3): TextureCoordinate =
        TextureCoordinate(
            u + other.x,
            v + other.y,
            w + other.z
        )

    override operator fun minus(other: Vec3): TextureCoordinate =
        TextureCoordinate(
            u - other.x,
            v - other.y,
            w - other.z
        )

    override operator fun div(other: Vec3): TextureCoordinate =
        TextureCoordinate(
            u / other.x,
            v / other.y,
            w / other.z
        )

    override operator fun div(other: Float): TextureCoordinate =
        TextureCoordinate(
            u / other,
            v / other,
            w / other
        )
}