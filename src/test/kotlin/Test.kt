import me.alex_s168.cad.Units
import me.alex_s168.math.vec.impl.Vec3f
import me.alex_s168.meshlib.Model
import me.alex_s168.meshlib.format.STLModelFormat
import me.alex_s168.meshlib.shapes.Box
import me.alex_s168.meshlib.shapes.PerfectCube

fun main() {
    val model = Model(
        STLModelFormat(
            units = Units.CENTIMETERS
        )
    )

    //val model = Model(
    //    OBJModelFormat(),
    //    STLModelFormat().loadFrom(
    //        ByteBuffer.wrap(java.io.File("test.stl").readBytes())
    //    )
    //)

    model.add(PerfectCube(Vec3f(0f, 10f, 0f), 10f))
    model.add(PerfectCube(Vec3f(0f, 10f, 0f), 5f))
    model.add(Box(Vec3f(0f, 0f, 0f), Vec3f(15f, 14f, 10f)))
    //model.sub(PerfectCube(Vec3(0f, 10f, 0f), 5f))

    println("tris: ${model.triangleCount}")

    val file = java.io.File("test.stl")
    file.writeBytes(model.toByteArray())
}