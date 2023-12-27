package me.alex_s168.meshlib.shapes

import me.alex_s168.math.AABB3f
import me.alex_s168.math.EnclosedVolume3
import me.alex_s168.math.vec.impl.Vec3f

/**
 * A box is a cube with a start and end point.
 *
 * @param start The start point of the box.
 * @param end The end point of the box.
 */
data class Box (
    val start: Vec3f,
    val end: Vec3f
): Cube(
    Vec3f(start.x, start.y, start.z),
    Vec3f(end.x, start.y, start.z),
    Vec3f(end.x, end.y, start.z),
    Vec3f(start.x, end.y, start.z),
    Vec3f(start.x, start.y, end.z),
    Vec3f(end.x, start.y, end.z),
    Vec3f(end.x, end.y, end.z),
    Vec3f(start.x, end.y, end.z)
), EnclosedVolume3 {

    /**
     * Creates a box from an AABB.
     */
    constructor(aabb: AABB3f):
            this(aabb.min, aabb.max)

    override val bb: AABB3f
        get() = AABB3f(start, end)

    override fun isInside(point: Vec3f): Boolean =
        point.x >= start.x && point.x <= end.x &&
        point.y >= start.y && point.y <= end.y &&
        point.z >= start.z && point.z <= end.z

    override fun isInside(point: Vec3f, tolerance: Float): Boolean =
        point.x >= start.x - tolerance && point.x <= end.x + tolerance &&
        point.y >= start.y - tolerance && point.y <= end.y + tolerance &&
        point.z >= start.z - tolerance && point.z <= end.z + tolerance

}