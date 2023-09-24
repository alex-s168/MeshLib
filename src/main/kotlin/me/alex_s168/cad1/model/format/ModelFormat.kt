package me.alex_s168.cad1.model.format

import me.alex_s168.cad1.model.ModelRaw
import java.nio.ByteBuffer

/**
 * The ModelFormat interface.
 * This interface is used to define a model format.
 */
interface ModelFormat {

    /**
     * The name of the format.
     */
    fun getName(): String

    /**
     * The file extension of the format.
     */
    fun getExtension(): String

    /**
     * Weather or not the format supports saving.
     */
    fun hasSaveSupport(): Boolean

    /**
     * Weather or not the format supports loading.
     */
    fun hasLoadSupport(): Boolean

    /**
     * Loads a model from a byte buffer
     */
    fun loadFrom(buf: ByteBuffer): ModelRaw

    /**
     * Saves a model to a byte buffer
     */
    fun saveTo(buf: ByteBuffer, model: ModelRaw)

    /**
     * Saves a model to a byte array
     */
    fun save(model: ModelRaw): ByteArray {
        val buf = ByteBuffer.allocate(estimateSize(model))
        saveTo(buf, model)
        buf.flip()
        return buf.array()
    }

    /**
     * Estimates the size of a model in bytes
     */
    fun estimateSize(model: ModelRaw): Int

}