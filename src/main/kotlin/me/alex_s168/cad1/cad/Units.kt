package me.alex_s168.cad1.cad

/**
 * Units used in the model.
 */
enum class Units(
    /**
     * The short name of the unit.
     */
    val textName: String,

    internal val stlName: String
) {
    MILLIMETERS(
        "mm",
        "mm"
    ),
    CENTIMETERS(
        "cm",
        "cm"
    ),
    METERS(
        "m",
        " m"
    ),
    FEET(
        "ft",
        "ft"
    ),
    INCHES(
        "in",
        "in"
    ),
    LIGHTYEARS(
        "ly",
        "ly"
    );

    companion object {
        /**
         * Returns the unit from the given string.
         */
        fun fromString(textName: String): Units? =
            entries.find { it.textName == textName }

        internal fun fromSTLString(stlName: String): Units? =
            entries.find { it.stlName == stlName }
    }
}