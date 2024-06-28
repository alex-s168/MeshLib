package me.alex_s168.meshlib.format

import me.alex_s168.meshlib.Material

interface MaterialFormat {
    /**
     * The name of the format.
     */
    fun getName(): String

    /**
     * The file extension of the format.
     */
    fun getExtension(): String

    /**
     * Loads a material from a string
     */
    fun loadFrom(str: String): List<Material>
}