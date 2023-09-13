package model.shapes

import math.Vec3

data class Box (
    val start: Vec3,
    val end: Vec3
): Cube (
    Vec3(start.x, start.y, start.z),
    Vec3(end.x, start.y, start.z),
    Vec3(end.x, end.y, start.z),
    Vec3(start.x, end.y, start.z),
    Vec3(start.x, start.y, end.z),
    Vec3(end.x, start.y, end.z),
    Vec3(end.x, end.y, end.z),
    Vec3(start.x, end.y, end.z)
)