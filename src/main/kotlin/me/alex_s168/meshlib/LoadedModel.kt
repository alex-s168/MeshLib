package me.alex_s168.meshlib

data class LoadedModel(
    val groups: MutableMap<String, ModelRaw> = mutableMapOf(),
    val materials: MutableMap<String, Material> = mutableMapOf(),
) {
    /** add all groups and materials from the other model into this */
    operator fun plusAssign(other: LoadedModel) {
        groups += other.groups
        materials += other.materials
    }

    /** throws an error if a material required for a [ModelRaw] is missing */
    fun verify() {
        groups.values.forEach { model ->
            model.material?.let { mat ->
                if (!materials.contains(mat)) {
                    error("Unresolved material $mat")
                }
            }
        }
    }
}