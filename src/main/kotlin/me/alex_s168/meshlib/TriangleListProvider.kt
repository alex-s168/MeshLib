package me.alex_s168.meshlib

/**
 * A provider of a list of triangles.
 */
interface TriangleListProvider {

    /**
     * Returns the list of triangles.
     */
    fun get(): List<Triangle>

    companion object {

        /**
         * Returns a provider of a list of triangles.
         */
        fun of(vararg tris: Triangle): TriangleListProvider =
            of(tris.toList())

        /**
         * Returns a provider of a list of triangles.
         */
        fun of(tris: List<Triangle>): TriangleListProvider =
            object: TriangleListProvider {
                override fun get() = tris
            }

    }

}