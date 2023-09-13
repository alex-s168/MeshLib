package model.format

import model.ModelRaw
import java.nio.ByteBuffer

interface ModelFormat {

    fun getName(): String

    fun getExtension(): String

    fun hasSaveSupport(): Boolean
    fun hasLoadSupport(): Boolean

    fun loadFrom(buf: ByteBuffer): ModelRaw
    fun saveTo(buf: ByteBuffer, model: ModelRaw)

    fun save(model: ModelRaw): ByteArray {
        val buf = ByteBuffer.allocate(estimateSize(model))
        saveTo(buf, model)
        buf.flip()
        return buf.array()
    }

    fun estimateSize(model: ModelRaw): Int

}