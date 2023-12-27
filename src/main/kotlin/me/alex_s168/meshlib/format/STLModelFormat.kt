package me.alex_s168.meshlib.format

import me.alex_s168.cad.Units
import me.alex_s168.math.vec.impl.Vec3f
import me.alex_s168.meshlib.Mesh
import me.alex_s168.meshlib.ModelRaw
import me.alex_s168.meshlib.Triangle
import me.alex_s168.meshlib.exception.InvalidModelFileException
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * STL model format.
 *
 * This model supports saving, loading, size calculation and saving / loading to / from a byte buffer.
 *
 * @property units Units used in the model.
 */
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


        units = Units.fromSTLString((header["UNITS"] ?: "mm").substring(0..1)) ?: Units.MILLIMETERS

        val amount = try {
            buf.getInt()
        } catch (_: Exception) {
            throw InvalidModelFileException(getName())
        }

        val model = ModelRaw(Mesh(ArrayList(amount), ArrayList(amount)))

        for (i in 0..<amount) {
            val normal = Vec3f().also { it.from(buf) }
            val a = Vec3f().also { it.from(buf) }
            val b = Vec3f().also { it.from(buf) }
            val c = Vec3f().also { it.from(buf) }
            buf.getShort() // unused

            model.mesh.add(Triangle(normal, a, b, c))
        }

        return model
    }

    override fun saveTo(buf: ByteBuffer, model: ModelRaw) {
        buf.order(ByteOrder.LITTLE_ENDIAN)

        val oldSize = buf.position()

        buf.put("UNITS=${units.stlName}".toByteArray())

        buf.position(oldSize + 80)

        buf.putInt(model.mesh.size)
        model.mesh.triangles.forEach { tri ->
            tri.normal.writeTo(buf)
            tri.a.writeTo(buf)
            tri.b.writeTo(buf)
            tri.c.writeTo(buf)
            buf.putShort(0)
        }
    }

    override fun estimateSize(model: ModelRaw): Int =
        84 + model.mesh.size * 50

}