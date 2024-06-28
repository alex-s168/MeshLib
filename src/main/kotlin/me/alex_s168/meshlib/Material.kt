package me.alex_s168.meshlib

import me.alex_s168.math.vec.impl.Vec3f

data class Material(
    var name: String,
    var ambientColor: Vec3f? = null,
    var diffuseColor: Vec3f? = null,
    var specularColor: Vec3f? = null,
    var specularExponent: Float? = null,
    /** transparency = 1 - dissolve */
    var transparency: Float? = null,
    var transmissionFilterColor: Vec3f? = null,
    var opticalDensity: Float? = null,
    val illuminationModel: MutableList<IlluminationModel> = mutableListOf(),
    var ambientMapFile: String? = null,
    var diffuseMapFile: String? = null,
    var specularMapFile: String? = null,
    var specularExponentMapFile: String? = null,
    /** transparency = 1 - dissolve */
    var dissolveMapFile: String? = null,
) {
    /** sorted as per OBJ format */
    enum class IlluminationModel {
        /** Color on and Ambient off */
        COLOR_ON_AMBIENT_OFF,
        /** Color on and Ambient on */
        COLOR_ON_AMBIENT_ON,
        /** Highlight on */
        HIGHLIGHT_ON,
        /** Reflection on and Ray trace on */
        REFLECTION_ON_RAYTRACE_ON,
        /** Transparency: Glass on, Reflection: Ray trace on */
        GLASS_ON_RAYTRACE_ON,
        /** Reflection: Fresnel on and Ray trace on */
        FRESNEL_ON_RAYTRACE_ON,
        /** Transparency: Refraction on, Reflection: Fresnel off and Ray trace on */
        REFRACTION_ON_FRESNEL_OFF_RAYTRACE_ON,
        /** Transparency: Refraction on, Reflection: Fresnel on and Ray trace on */
        REFRACTION_ON_FRESNEL_ON_RAYTRACE_ON,
        /** Reflection on and Ray trace off */
        REFLECTION_ON_RAYTRACE_OFF,
        /** Transparency: Glass on, Reflection: Ray trace off */
        GLASS_ON_RAYTRACE_OFF,
        /** Casts shadows onto invisible surfaces */
        SHADOWS_ONTO_INVISIBLE_SURFACES,
    }
}
