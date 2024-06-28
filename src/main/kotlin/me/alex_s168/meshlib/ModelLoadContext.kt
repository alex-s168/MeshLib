package me.alex_s168.meshlib

import me.alex_s168.meshlib.format.Format

/**
 * The type of a import in a [ModelLoadContext].
 */
enum class ImportType {
    MATERIAL_LIBRARY
}

/**
 * The return value of [me.alex_s168.meshlib.format.ModelFormat.loadFrom].
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
) {
    override fun toString() =
        "${groups.joinToString("\n")}\n$imports"

    /**
     * Combine this model with another model
     */
    operator fun plus(other: ModelLoadContext) =
        ModelLoadContext(groups + other.groups, imports + other.imports)

    /**
     * Load all imported files like material files and create a [LoadedModel]
     * [LoadedModel.verify] should be called on the result
     */
    fun load(readFile: (String) -> String): LoadedModel {
        val res = LoadedModel()

        groups.forEach {
            res.groups[it.name] = it
        }

        imports.forEach { (type, import) ->
            when (type) {
                ImportType.MATERIAL_LIBRARY -> {
                    val fmt = runCatching {
                        Format.materialByExtension[import.split(".").last()]!!
                    }.getOrElse {
                        error("Failed to detect format of material file $import")
                    }

                    val text = runCatching {
                        readFile(import)
                    }.getOrElse {
                        error("Failed to readFile $import : $it")
                    }

                    val loaded = runCatching {
                        fmt.loadFrom(text)
                    }.getOrElse {
                        error("While loading file $import: $it")
                    }

                    loaded.forEach {
                        res.materials[it.name] = it
                    }
                }
            }
        }

        return res
    }
}