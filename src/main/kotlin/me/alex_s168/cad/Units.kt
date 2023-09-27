package me.alex_s168.cad

/**
 * Units used in the model.
 */
enum class Units(
    /**
     * The short name of the unit.
     */
    val textName: String,

    internal val stlName: String,

    private val toMM: Double
) {
    MILLIMETERS(
        "mm",
        "mm",
        1.0
    ),
    CENTIMETERS(
        "cm",
        "cm",
        10.0
    ),
    METERS(
        "m",
        " m",
        1000.0
    ),
    FEET(
        "ft",
        "ft",
        304.8
    ),
    INCHES(
        "in",
        "in",
        25.4
    ),
    LIGHTYEARS(
        "ly",
        "ly",
        946073047258080000000.0
    );

    fun to(unit: Units, value: Double): Double =
        value * toMM / unit.toMM

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