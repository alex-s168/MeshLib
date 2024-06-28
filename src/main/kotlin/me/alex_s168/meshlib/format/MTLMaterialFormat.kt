package me.alex_s168.meshlib.format

import me.alex_s168.math.vec.impl.Vec3f
import me.alex_s168.meshlib.Material

class MTLMaterialFormat @Deprecated("", replaceWith = ReplaceWith("Format.MTL")) constructor(): MaterialFormat {
    override fun getName() =
        "MTL"

    override fun getExtension() =
        "mtl"

    override fun loadFrom(str: String): List<Material> {
        val res = mutableListOf<Material>()
        var mtl: Material? = null

        str.lines().forEachIndexed { lineNum, lineX ->
            val line = lineX.trim()

            if (line.firstOrNull() == '#') return@forEachIndexed
            if ((line.firstOrNull() ?: ' ') == ' ') return@forEachIndexed

            val segments = line.split(" ")

            fun argsAsFloatVec() =
                runCatching {
                    Vec3f(segments.drop(1).map { it.toFloat() }.toFloatArray())
                }.getOrElse {
                    error("expected three floats")
                }

            fun argsAsFloat() =
                runCatching {
                    segments[1].toFloat()
                }.getOrElse {
                    error("expected float")
                }

            fun argsAsInt() =
                runCatching {
                    segments[1].toInt()
                }.getOrElse {
                    error("expected int")
                }

            fun argsAsStr() =
                segments.drop(1).joinToString(" ")

            try {
                when (segments[0]) {
                    "newmtl" -> {
                        mtl?.let { res += it }
                        mtl = Material(segments[1])
                    }

                    "Ka" -> {
                        mtl ?: error("missing newmtl")
                        mtl!!.ambientColor = argsAsFloatVec()
                    }

                    "Kd" -> {
                        mtl ?: error("missing newmtl")
                        mtl!!.diffuseColor = argsAsFloatVec()
                    }

                    "Ks" -> {
                        mtl ?: error("missing newmtl")
                        mtl!!.specularColor = argsAsFloatVec()
                    }

                    "Ns" -> {
                        mtl ?: error("missing newmtl")
                        mtl!!.specularExponent = argsAsFloat()
                    }

                    "d" -> {
                        mtl ?: error("missing newmtl")
                        mtl!!.transparency = 1 - argsAsFloat()
                    }

                    "Tr" -> {
                        mtl ?: error("missing newmtl")
                        mtl!!.transparency = argsAsFloat()
                    }

                    "Tf" -> {
                        mtl ?: error("missing newmtl")
                        mtl!!.transmissionFilterColor = argsAsFloatVec()
                    }

                    "Ni" -> {
                        mtl ?: error("missing newmtl")
                        mtl!!.opticalDensity = argsAsFloat()
                    }

                    "illum" -> {
                        mtl ?: error("missing newmtl")
                        val id = argsAsInt()
                        mtl!!.illuminationModel += Material.IlluminationModel.entries.getOrNull(id)
                            ?: error("invalid illum model")
                    }

                    "map_Ka" -> {
                        mtl ?: error("missing newmtl")
                        mtl!!.ambientMapFile = argsAsStr()
                    }

                    "map_Kd" -> {
                        mtl ?: error("missing newmtl")
                        mtl!!.diffuseMapFile = argsAsStr()
                    }

                    "map_Ks" -> {
                        mtl ?: error("missing newmtl")
                        mtl!!.specularMapFile = argsAsStr()
                    }

                    "map_Ns" -> {
                        mtl ?: error("missing newmtl")
                        mtl!!.specularExponentMapFile = argsAsStr()
                    }

                    "map_d" -> {
                        mtl ?: error("missing newmtl")
                        mtl!!.diffuseMapFile = argsAsStr()
                    }
                }
            } catch (e: Exception) {
                error("in line $lineNum : $e")
            }
        }

        mtl?.let { res += it }
        return res
    }
}