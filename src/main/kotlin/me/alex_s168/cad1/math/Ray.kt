package me.alex_s168.cad1.math

/**
 * A ray is a line with an origin and a direction.
 * @param origin The origin of the ray.
 * @param direction The direction of the ray (should be normalized).
 */
data class Ray (
    val origin: Vec3,
    val direction: Vec3
)