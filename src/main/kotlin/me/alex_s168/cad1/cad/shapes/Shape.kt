package me.alex_s168.cad1.cad.shapes

import me.alex_s168.cad1.model.TriangleListProvider

/**
 * A shape is a 3D object that can be converted to a list of triangles.
 */
interface Shape {

    /**
     * Converts the shape to a list of triangles.
     *
     * @param accuracy The accuracy of the conversion. The higher the accuracy, the more triangles will be generated.
     * @return A list of triangles.
     */
    fun toTriangles(accuracy: Float): TriangleListProvider

}