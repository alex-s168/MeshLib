package cad.shapes

import model.TriangleListProvider

interface Shape {

    fun toTriangles(accuracy: Float): TriangleListProvider

}