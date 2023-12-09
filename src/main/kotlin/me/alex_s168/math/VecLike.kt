package me.alex_s168.math

/**
 * Interface for vector-like objects.
 *
 * VecLike-objects are immutable and all operations return a new object.
 *
 * @param T The type of the vector-like object.
 *
 * @see me.alex_s168.math.Vec3
 */
interface VecLike<T> {

    operator fun plus(other: T): T

    operator fun minus(other: T): T

    operator fun times(other: T): T
    operator fun times(other: Int): T
    operator fun times(other: Float): T

    operator fun div(other: T): T
    operator fun div(other: Int): T
    operator fun div(other: Float): T

    operator fun unaryMinus(): T

    /**
     * Calculates the dot product of this vector and [other].
     */
    fun dot(other: T): Float

    /**
     * Calculates the cross product of this vector and [other].
     */
    fun cross(other: T): T

    /**
     * Calculates the length of this vector.
     */
    fun length(): Float

    /**
     * Returns a normalized vector with the same direction as this vector.
     */
    fun normalize(): T

    /**
     * Linearly interpolates between this vector and [other] by [t].
     */
    fun lerp(other: T, t: Float): T

    /**
     * Linearly interpolates between this vector and [other] by [t].
     */
    infix fun lerp(other: Pair<Float, T>): T
}