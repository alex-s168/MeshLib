package me.alex_s168.meshlib

import me.alex_s168.math.vec.impl.Vec3f
import me.alex_s168.meshlib.format.ModelFormat
import java.nio.ByteBuffer

/**
 * A safe wrapper for [ModelRaw] that provides a [ModelFormat] to save the model.
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
    fun estimateSize(): Int =
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
    @Throws(UnsupportedOperationException::class)
    fun save(buf: ByteBuffer) {
        if (!format.hasSaveSupport()) {
            throw UnsupportedOperationException("This format does not support saving!")
        }
        format.saveTo(buf, raw)
    }

    /**
     * Adds triangles to the model.
     */
    @Suppress("DEPRECATION")
    fun add(tris: TriangleListProvider) {
        raw.mesh.add(tris)
        raw.mesh.bbUnsafe = raw.mesh.bbUnsafe with tris.aabb()
    }

    /**
     * Adds triangles to the model.
     */
    fun add(tris: List<Triangle>) =
        add(TriangleListProvider.of(tris))

    /**
     * The amount of triangles in the model.
     */
    @Suppress("DEPRECATION")
    val triangleCount: Int
        get() =
            raw.mesh.size

    /**
     * The corners of the mesh.
     */
    @Suppress("DEPRECATION")
    val corners: Iterable<Vec3f>
        get() =
            raw.mesh.corners

    /**
     * The name of the material of the model.
     */
    @Suppress("DEPRECATION")
    var material: String
        get() =
            raw.material

        set(value) {
            raw.material = value
        }

    /**
     * The name of the model.
     */
    @Suppress("DEPRECATION")
    var name: String
        get() =
            raw.name

        set(value) {
            raw.name = value
        }
}