package me.alex_s168.meshlib.texture

import me.alex_s168.math.vec.FloatVecLike
import me.alex_s168.math.vec.VecLike
import me.alex_s168.math.vec.impl.Vec3f
import java.nio.ByteBuffer
import java.nio.FloatBuffer

/**
 * A texture coordinate
 */
class TextureCoordinate(
    var vec: Vec3f
): FloatVecLike<TextureCoordinate> {
    override fun hashCode(): Int =
        vec.hashCode()

    override fun equals(other: Any?): Boolean =
        other is TextureCoordinate && vec == other.vec

    override fun toString() =
        "$vec"

    override val size: Int
        get() = 3

    override fun asArray(): FloatArray =
        vec.asArray()

    constructor(x: Float, y: Float, z: Float):
            this(Vec3f(x, y, z))

    constructor():
            this(Vec3f())

    var u
        get() =
            vec.x
        set(value) {
            vec.x = value
        }

    var v
        get() =
            vec.y
        set(value) {
            vec.y = value
        }

    var w
        get() =
            vec.z
        set(value) {
            vec.z = value
        }

    override fun new(): TextureCoordinate =
        TextureCoordinate(Vec3f())

    override fun copy(): TextureCoordinate =
        TextureCoordinate(vec.copy())

    override fun from(other: FloatBuffer) {
        vec.from(other)
    }

    override fun from(other: FloatArray) {
        vec.from(other)
    }

    override fun from(buf: ByteBuffer) {
        vec.from(buf)
    }

    override fun from(other: VecLike<Float, TextureCoordinate>) {
        vec = other.copy().vec
    }

    override fun get(index: Int): Float =
        vec[index]

    override fun set(index: Int, value: Float) {
        vec[index] = value
    }

    override fun toArray(): FloatArray =
        vec.toArray()

    override fun writeTo(buffer: FloatBuffer) {
        vec.writeTo(buffer)
    }

    override fun writeTo(arr: FloatArray, offset: Int) {
        vec.writeTo(arr, offset)
    }

    override fun writeTo(buf: ByteBuffer) {
        vec.writeTo(buf)
    }

    override fun zeroSelf(): TextureCoordinate {
        vec.zeroSelf()
        return this
    }
}