package me.alex_s168.meshlib.shapes

import me.alex_s168.math.AABB
import me.alex_s168.math.Vec3

/**
 * A box is a cube with a start and end point.
 *
 * @param start The start point of the box.
 * @param end The end point of the box.
 */
data class Box (
    val start: Vec3,
    val end: Vec3
): Cube(
    Vec3(start.x, start.y, start.z),
    Vec3(end.x, start.y, start.z),
    Vec3(end.x, end.y, start.z),
    Vec3(start.x, end.y, start.z),
    Vec3(start.x, start.y, end.z),
    Vec3(end.x, start.y, end.z),
    Vec3(end.x, end.y, end.z),
    Vec3(start.x, end.y, end.z)
) {

    /**
     * Creates a box from an AABB.
     */
    constructor(aabb: AABB):
            this(aabb.min, aabb.max)

}