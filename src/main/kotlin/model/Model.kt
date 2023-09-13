package model

import model.format.ModelFormat
import java.nio.ByteBuffer

data class Model (
    var format: ModelFormat,
    val raw: ModelRaw = ModelRaw()
) {

    @Deprecated("Not supported with some model formats!", ReplaceWith(""))
    fun getSize(): Int =
        format.estimateSize(raw)

    fun toByteArray(): ByteArray =
        format.save(raw)

    @Deprecated("Not supported with some model formats!", ReplaceWith(""))
    fun save(buf: ByteBuffer) =
        format.saveTo(buf, raw)

    fun add(tris: TriangleListProvider) {
        raw.mesh.add(tris)
        raw.mesh.aabbUnsafe = raw.mesh.aabbUnsafe with tris.aabb()
    }

    fun add(tris: List<Triangle>) =
        add(TriangleListProvider.of(tris))

    fun sub(mesh: Mesh) {
        raw.mesh.subtractMesh(mesh)
        raw.mesh.updateAABB()
    }

    fun sub(tris: TriangleListProvider) =
        sub(Mesh(tris.get().toMutableList()))

}