package me.alex_s168.meshlib

import me.alex_s168.meshlib.texture.TextureFace

/**
 * A face is a triangle with optional texture coordinates.
 */
data class Face(
    /**
     * The triangle of the face.
     */
    val tri: Triangle,

    /**
     * The texture coordinate of the face.
     */
    val tex: TextureFace?
)