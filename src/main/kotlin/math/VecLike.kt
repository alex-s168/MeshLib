package math

interface VecLike<T> {

    operator fun plus(other: T): T

    operator fun minus(other: T): T

    operator fun times(other: T): T
    operator fun times(other: Int): T
    operator fun times(other: Float): T

    operator fun div(other: T): T
    operator fun div(other: Int): T
    operator fun div(other: Float): T

    fun dot(other: T): Float
    fun cross(other: T): T
    fun length(): Float
    fun normalize(): T
}