package model.format

import cad.Units
import math.Vec3
import model.Mesh
import model.ModelRaw
import model.Triangle
import model.exception.InvalidModelFileException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class STLModelFormat(
    var units: Units = Units.MILLIMETERS
): ModelFormat {

    override fun getName(): String = "STL"

    override fun getExtension(): String = "stl"

    override fun hasSaveSupport(): Boolean = true

    override fun hasLoadSupport(): Boolean = true

    @Throws(InvalidModelFileException::class)
    override fun loadFrom(buf: ByteBuffer): ModelRaw {
        buf.order(ByteOrder.LITTLE_ENDIAN)

        val headerRaw = ByteArray(80)
        try {
            buf.get(headerRaw)
        } catch (_: Exception) {
            throw InvalidModelFileException(getName())
        }
        val headerAll = String(headerRaw).split(",")
        val header = HashMap<String, String>()
        headerAll.forEach { line ->
            val parts = line.split("=")
            if (parts.size != 2) {
                throw InvalidModelFileException(getName())
            }
            header[parts[0]] = parts[1]
        }


        units = Units.fromString((header["UNITS"] ?: "mm").substring(0..1)) ?: Units.MILLIMETERS

        val amount = try {
            buf.getInt()
        } catch (_: Exception) {
            throw InvalidModelFileException(getName())
        }

        val model = ModelRaw(Mesh(ArrayList(amount)))

        for (i in 0..<amount) {
            val normal = Vec3.from(buf)
            val a = Vec3.from(buf)
            val b = Vec3.from(buf)
            val c = Vec3.from(buf)
            buf.getShort() // unused

            model.mesh.triangles.add(Triangle(normal, a, b, c))
        }

        return model
    }

    override fun saveTo(buf: ByteBuffer, model: ModelRaw) {
        buf.order(ByteOrder.LITTLE_ENDIAN)

        val oldSize = buf.position()

        buf.put("UNITS=${units.s}".toByteArray())

        buf.position(oldSize + 80)

        buf.putInt(model.mesh.triangles.size)
        model.mesh.triangles.forEach { tri ->
            tri.normal.save(buf)
            tri.a.save(buf)
            tri.b.save(buf)
            tri.c.save(buf)
            buf.putShort(0)
        }
    }

    override fun estimateSize(model: ModelRaw): Int =
        84 + model.mesh.triangles.size * 50

}