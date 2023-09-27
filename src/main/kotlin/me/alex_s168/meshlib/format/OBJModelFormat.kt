package me.alex_s168.meshlib.format

import me.alex_s168.math.Vec3
import me.alex_s168.meshlib.exception.InvalidModelFileException
import me.alex_s168.meshlib.texture.TextureCoordinate
import me.alex_s168.meshlib.texture.TextureFace
import me.alex_s168.meshlib.*
import java.lang.StringBuilder
import java.nio.ByteBuffer

/**
 * The OBJ model format.
 * This model supports saving and size ESTIMATION
 */
class OBJModelFormat: ModelFormat {

    override fun getName(): String =
        "OBJ"

    override fun getExtension(): String =
        "obj"

    override fun hasSaveSupport(): Boolean =
        true

    override fun hasLoadSupport(): Boolean =
        true

    override fun loadFrom(buf: ByteBuffer): ModelRaw =
        throw UnsupportedOperationException("OBJModelFormat does not support loading from a byte buffer!")

    override fun loadFrom(str: String): ModelLoadContext = try {
        loadFromUnsafe(str)
    } catch (e: Exception) {
        throw InvalidModelFileException("Failed to load model from string. debug: ${e.message}")
    }

    private fun loadFromUnsafe(str: String): ModelLoadContext {
        var name = ""
        val vertecies = mutableListOf<Vec3>()
        val texCords = mutableListOf<TextureCoordinate>()
        val normals = mutableListOf<Vec3>()
        val faces = mutableListOf<Face>()

        val materialLibs = mutableListOf<String>()
        var material = ""

        val groups = mutableSetOf<ModelRaw>()
        var currg = ""

        for (lineX in str.lines()) {
            val line = lineX.trim()

            if (line.firstOrNull() == '#') continue
            if ((line.firstOrNull() ?: ' ') == ' ') continue

            if (line.startsWith("o ")) {
                if (name != "")
                    throw UnsupportedOperationException("OBJModelFormat does not support multiple objects")
                name = line.substring(2)
                continue
            }

            if (line.startsWith("v ")) {
                val parts = line.substring(2).split(' ')
                vertecies += Vec3(
                    parts[0].toFloat(), // x
                    parts[1].toFloat(), // y
                    parts[2].toFloat()  // z
                )
                continue
            }

            if (line.startsWith("vt ")) {
                val parts = line.substring(3).split(' ')
                texCords += TextureCoordinate(
                    parts[0].toFloat(), // u
                    parts.getOrNull(1)?.toFloat() ?: 0f, // v
                    parts.getOrNull(2)?.toFloat() ?: 0f  // w
                )
                continue
            }

            if (line.startsWith("vn ")) {
                val parts = line.substring(3).split(' ')
                normals += Vec3(
                    parts[0].toFloat(),
                    parts[1].toFloat(),
                    parts[2].toFloat()
                )
                continue
            }

            if (line.startsWith("f ")) {
                val parts = line.substring(2).split(' ')

                // returns v, vt, vn
                fun handlePart(part: String): Triple<Vec3, TextureCoordinate?, Vec3?> {
                    val p = part.split('/')
                    if (p.isEmpty())
                        throw UnsupportedOperationException("OBJModelFormat does not support this line: $line")

                    val v_n = p[0].toInt()
                    val v = if (v_n < 0) vertecies[vertecies.size + v_n] else vertecies[v_n - 1]

                    val vt = if (p.size > 1 && p[1] != "") {
                        val vt_n = p[1].toIntOrNull()
                        if (vt_n == null) null
                        else if (vt_n < 0) texCords[texCords.size + vt_n] else texCords[vt_n - 1]
                    } else null

                    val vn = if (p.size > 2 && p[2] != "") {
                        val vn_n = p[2].toInt()
                        if (vn_n < 0) normals[normals.size + vn_n] else normals[vn_n - 1]
                    } else null

                    return Triple(
                        v,
                        vt,
                        vn
                    )
                }

                val (v0, vt0, vn0) = handlePart(parts[0])
                val (v1, vt1, vn1) = handlePart(parts[1])
                val (v2, vt2, vn2) = handlePart(parts[2])

                faces += Face(
                    Triangle(vn0 ?: Vec3(), v0, v1, v2),
                    vt0?.let { TextureFace(vt0, vt1!!, vt2!!) }
                )

                continue
            }

            if (line.startsWith("g ")) {
                if (currg != "") {
                    groups += ModelRaw(
                        Mesh(faces.toList()),
                        currg,
                        material
                    )
                    faces.clear()
                }
                currg = line.substring(2)
                continue
            }

            // shading is not implemented
            if (line.startsWith("s ")) {
                continue
            }

            if (line.startsWith("usemtl ")) {
                material = line.substring(7)
                continue
            }

            if (line.startsWith("mtllib ")) {
                materialLibs += line.substring(7)
                continue
            }

            throw UnsupportedOperationException("OBJModelFormat does not support this line: $line")
        }

        groups += ModelRaw(
            Mesh(faces),
            currg,
            material
        )

        return ModelLoadContext(
            groups,
            materialLibs.map { ImportType.MATERIAL_LIBRARY to it }
        )
    }

    override fun save(model: ModelRaw): ByteArray {
        val out = StringBuilder()

        out.append("# generated by experimental obj generator\n\n")

        model.mesh.triangles.forEach {
            out.append("v ${it.a.x} ${it.a.y} ${it.a.z}\n")
            out.append("v ${it.b.x} ${it.b.y} ${it.b.z}\n")
            out.append("v ${it.c.x} ${it.c.y} ${it.c.z}\n")

            out.append("f -1 -2 -3\n\n")
        }

        return out.toString().toByteArray()
    }

    override fun saveTo(buf: ByteBuffer, model: ModelRaw) {
        buf.put(save(model))
    }

    override fun estimateSize(model: ModelRaw): Int =
        43 + (model.mesh.size * 40 * 3 + 20)

}