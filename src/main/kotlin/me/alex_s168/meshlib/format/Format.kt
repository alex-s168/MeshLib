package me.alex_s168.meshlib.format

object Format {
    /** all material formats */
    val materialByExtension = mutableMapOf<String, MaterialFormat>()
    /** all model formats */
    val modelByExtension = mutableMapOf<String, ModelFormat>()

    fun register(format: MaterialFormat) {
        materialByExtension[format.getExtension()] = format
    }

    fun register(model: ModelFormat) {
        modelByExtension[model.getExtension()] = model
    }

    @Suppress("deprecation")
    val OBJ: ModelFormat = OBJModelFormat()
    val STL: ModelFormat = STLModelFormat()

    @Suppress("deprecation")
    val MTL: MaterialFormat = MTLMaterialFormat()

    init {
        register(OBJ)
        register(STL)

        register(MTL)
    }
}