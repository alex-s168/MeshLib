package model

interface TriangleListProvider {

    fun get(): List<Triangle>

    companion object {

        fun of(vararg tris: Triangle): TriangleListProvider =
            of(tris.toList())

        fun of(tris: List<Triangle>): TriangleListProvider =
            object: TriangleListProvider {
                override fun get() = tris
            }

    }

}