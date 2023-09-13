import cad.Units
import math.Vec3
import model.Model
import model.format.STLModelFormat
import model.shapes.Box
import model.shapes.PerfectCube

fun main(args: Array<String>) {
    val model = Model(STLModelFormat(
        units=Units.CENTIMETERS
    ))

    //val model = Model(
    //    OBJModelFormat(),
    //    STLModelFormat().loadFrom(
    //        ByteBuffer.wrap(java.io.File("test.stl").readBytes())
    //    )
    //)

    model.add(PerfectCube(Vec3(0f, 10f, 0f), 10f))
    model.add(PerfectCube(Vec3(0f, 10f, 0f), 5f))
    model.add(Box(Vec3(0f, 0f, 0f), Vec3(15f, 14f, 10f)))
    //model.sub(PerfectCube(Vec3(0f, 10f, 0f), 5f))

    println("tris: ${model.raw.mesh.triangles.size}")

    val file = java.io.File("test.stl")
    file.writeBytes(model.toByteArray())
}