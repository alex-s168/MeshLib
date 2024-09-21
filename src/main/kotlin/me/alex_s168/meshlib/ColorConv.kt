package me.alex_s168.meshlib

import me.alex_s168.math.color.Color

object ColorConv {
    fun argbToColor(argb: Int): Color { // TODO: add to mathlib
        val a = (argb shr 24) and 0xFF
        val r = (argb shr 16) and 0xFF
        val g = (argb shr 8) and 0xFF
        val b = argb and 0xFF
        return Color(r, g, b, a)
    }
}