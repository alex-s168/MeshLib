package me.alex_s168.cad1.model

import me.alex_s168.cad1.model.format.ModelFormat
import java.nio.ByteBuffer

/**
 * A safe wrapper for [ModelRaw] that provides a [ModelFormat] to save and load the model.
 */
data class Model (
    /**
     * The format of the model.
     */
    var format: ModelFormat,

    /**
     * The raw model data.
     * Accessing this directly is not recommended, use the methods provided by [Model] instead.
     * @see ModelRaw
     */
    @Deprecated(
        message = "Accessing this directly is not recommended!",
        level = DeprecationLevel.WARNING
    )
    val raw: ModelRaw = ModelRaw()
) {

    /**
     * Returns the size of the model in bytes for the current format.
     * Some formats may not support this operation.
     */
    @Deprecated(
        "Not supported with some model formats!",
        ReplaceWith("")
    )
    @Suppress("DEPRECATION")
    fun getSize(): Int =
        format.estimateSize(raw)

    /**
     * Saves the model as a byte array.
     */
    @Suppress("DEPRECATION")
    fun toByteArray(): ByteArray =
        format.save(raw)

    /**
     * Saves the model to a [ByteBuffer].
     * Some formats may not support this operation.
     * Use [toByteArray] instead.
     */
    @Deprecated(
        "Not supported with some model formats!",
        ReplaceWith("")
    )
    @Suppress("DEPRECATION")
    fun save(buf: ByteBuffer) =
        format.saveTo(buf, raw)

    /**
     * Adds triangles to the model.
     */
    @Suppress("DEPRECATION")
    fun add(tris: TriangleListProvider) {
        raw.mesh.add(tris)
        raw.mesh.aabbUnsafe = raw.mesh.aabbUnsafe with tris.aabb()
    }

    /**
     * Adds triangles to the model.
     */
    fun add(tris: List<Triangle>) =
        add(TriangleListProvider.of(tris))

    /**
     * Subtracts a mesh from the model.
     * (Boolean subtraction)
     * @see Mesh.subtractMesh
     */
    @Suppress("DEPRECATION")
    fun sub(mesh: Mesh) {
        raw.mesh.subtractMesh(mesh)
        raw.mesh.updateAABB()
    }

    /**
     * Subtracts triangles from the model.
     * (Boolean subtraction)
     * @see Mesh.subtractMesh
     */
    fun sub(tris: TriangleListProvider) =
        sub(Mesh(tris.get().toMutableList()))

}