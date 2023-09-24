package me.alex_s168.cad1.model

/**
 * ModelRaw is used to directly access the mesh.
 */
data class ModelRaw (
    /**
     * The mesh of the model.
     */
    val mesh: Mesh = Mesh(mutableListOf())
)