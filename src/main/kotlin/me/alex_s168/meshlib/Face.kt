package me.alex_s168.meshlib

import me.alex_s168.math.color.Color
import me.alex_s168.math.vec.impl.Vec2f
import me.alex_s168.math.vec.impl.Vec3f
import me.alex_s168.meshlib.texture.TextureFace
import java.awt.image.BufferedImage

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
) {
    override fun toString() =
        "($tri, $tex)"

    fun getPixelAt3dCoordinateARGB(point: Vec3f, tex: BufferedImage): Int {
        val coord = getTextureCoordinate(point)
        return tex.getRGB(coord.x.toInt(), coord.y.toInt())
    }

    fun getPixelAt3dCoordinate(point: Vec3f, tex: BufferedImage): Color =
        ColorConv.argbToColor(getPixelAt3dCoordinateARGB(point, tex))

    /** Function to compute the texture coordinate at a 3D point within the triangle */
    fun getTextureCoordinate(
        p: Vec3f
    ): Vec2f {
        tex!!

        val p1 = tri.a
        val p2 = tri.b
        val p3 = tri.c

        val t1 = tex.a
        val t2 = tex.b
        val t3 = tex.c

        // chatgpt:

        // Compute vectors from point p1
        val v0 = p2 - p1
        val v1 = p3 - p1
        val v2 = p - p1

        // Compute dot products
        val dot00 = v0 dot v0
        val dot01 = v0 dot v1
        val dot11 = v1 dot v1
        val dot02 = v0 dot v2
        val dot12 = v1 dot v2

        // Compute denominator of barycentric coordinates
        val denom = dot00 * dot11 - dot01 * dot01

        // Ensure we don't divide by zero
        if (denom == 0.0f) {
            throw IllegalArgumentException("The triangle is degenerate (vertices are collinear).")
        }

        // Compute barycentric coordinates
        val lambda2 = (dot11 * dot02 - dot01 * dot12) / denom
        val lambda3 = (dot00 * dot12 - dot01 * dot02) / denom
        val lambda1 = 1.0f - lambda2 - lambda3

        // Interpolate texture coordinates
        val u = (lambda1 * t1.u) + (lambda2 * t2.u) + (lambda3 * t3.u)
        val v = (lambda1 * t1.v) + (lambda2 * t2.v) + (lambda3 * t3.v)

        return Vec2f(u, v)
    }

}