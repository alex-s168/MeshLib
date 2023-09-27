package me.alex_s168.meshlib

/**
 * The type of a import in a [ModelLoadContext].
 */
enum class ImportType {
    MATERIAL_LIBRARY,
    TEXTURE
}

/**
 * The return value of [me.alex_s168.cad1.model.format.ModelFormat.loadFrom].
 */
data class ModelLoadContext(
    /**
     * The groups of the model.
     */
    val groups: Iterable<ModelRaw>,

    /**
     * The imports of the model.
     * @see ImportType
     */
    val imports: Iterable<Pair<ImportType, String>>,
)