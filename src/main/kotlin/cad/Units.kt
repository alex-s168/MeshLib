package cad

enum class Units(val s: String) {
    MILLIMETERS("mm"),
    CENTIMETERS("cm"),
    METERS(" m"),
    FEET("ft"),
    INCHES("in"),
    LIGHTYEARS("ly");

    companion object {
        fun fromString(s: String): Units? =
            entries.find { it.s == s }
    }
}