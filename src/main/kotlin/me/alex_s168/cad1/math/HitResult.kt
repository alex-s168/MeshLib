package me.alex_s168.cad1.math

/**
 * Represents the result of a ray cast.
 * @property hitPoint The point at which the ray hit the object.
 * @property hitNormal The normal of the object at the point of intersection.
 */
data class HitResult(
    val hitPoint: Vec3?,
    val hitNormal: Vec3?
)