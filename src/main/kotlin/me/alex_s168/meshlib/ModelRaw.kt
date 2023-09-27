package me.alex_s168.meshlib

/**
 * ModelRaw is used to directly access the mesh.
 */
data class ModelRaw (
    /**
     * The mesh of the model.
     */
    val mesh: Mesh = Mesh(mutableListOf(), mutableListOf()),

    /**
     * The name of the model.
     */
    var name: String = "",

    /**
     * The material of the model.
     */
    var material: String = ""
)