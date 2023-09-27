package me.alex_s168.meshlib.texture

/**
 * A texture face
 */
data class TextureFace(
    val a: TextureCoordinate = TextureCoordinate(0f),
    val b: TextureCoordinate = TextureCoordinate(0f),
    val c: TextureCoordinate = TextureCoordinate(0f)
)