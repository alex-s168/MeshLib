package me.alex_s168.meshlib.texture

import me.alex_s168.math.Vec3

/**
 * A texture coordinate
 */
data class TextureCoordinate(
    val u: Float,
    val v: Float = 0f,
    val w: Float = 0f,
): Vec3(u, v, w)