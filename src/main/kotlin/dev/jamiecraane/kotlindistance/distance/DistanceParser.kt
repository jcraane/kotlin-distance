package dev.jamiecraane.kotlindistance.distance

/**
 * Parses human-readable distance strings into [Distance].
 *
 * Supported units (case-insensitive, plural forms allowed):
 * - Metric: km, kilometer(s); m, meter(s); cm, centimeter(s); mm, millimeter(s)
 * - Imperial: mi, mile(s); yd, yard(s); ft, foot/feet; in, inch(es)
 *
 * Supported input styles:
 *  - "5km", "5 km", "5.0 kilometers", "3.2 miles", "100 m", "12FT", "1,5 km"
 *
 * Throws [IllegalArgumentException] with a descriptive message on invalid input.
 */
object DistanceParser {
    private val pattern = Regex(
        pattern = "^\\s*([+-]?\\d+(?:[.,]\\d+)?)\\s*([a-zA-Z]+)\\s*$",
        options = setOf(RegexOption.IGNORE_CASE)
    )

    private val metricUnits: Map<String, DistanceUnit> = mapOf(
        // kilometers
        "km" to DistanceUnit.KILOMETERS,
        "kilometer" to DistanceUnit.KILOMETERS,
        "kilometers" to DistanceUnit.KILOMETERS,
        // meters
        "m" to DistanceUnit.METERS,
        "meter" to DistanceUnit.METERS,
        "meters" to DistanceUnit.METERS,
        // centimeters
        "cm" to DistanceUnit.CENTIMETERS,
        "centimeter" to DistanceUnit.CENTIMETERS,
        "centimeters" to DistanceUnit.CENTIMETERS,
        // millimeters
        "mm" to DistanceUnit.MILLIMETERS,
        "millimeter" to DistanceUnit.MILLIMETERS,
        "millimeters" to DistanceUnit.MILLIMETERS,
    )

    private data class ImperialUnit(val mmPerUnit: Double)

    private val imperialUnits: Map<String, ImperialUnit> = mapOf(
        // inches
        "in" to ImperialUnit(25.4),
        "inch" to ImperialUnit(25.4),
        "inches" to ImperialUnit(25.4),
        // feet
        "ft" to ImperialUnit(304.8),
        "foot" to ImperialUnit(304.8),
        "feet" to ImperialUnit(304.8),
        // yards
        "yd" to ImperialUnit(914.4),
        "yard" to ImperialUnit(914.4),
        "yards" to ImperialUnit(914.4),
        // miles
        "mi" to ImperialUnit(1_609_344.0),
        "mile" to ImperialUnit(1_609_344.0),
        "miles" to ImperialUnit(1_609_344.0),
    )

    /**
     * Parse the given [input] into a [Distance].
     *
     * @throws IllegalArgumentException if the format is invalid or the unit is unsupported.
     */
    fun parse(input: String): Distance {
        val match = pattern.matchEntire(input)
            ?: throw IllegalArgumentException("Invalid distance format: '$input'. Expected '<number><optional space><unit>' like '5 km', '5km', or '3.2 miles'.")

        val (rawNumber, rawUnit) = match.destructured
        val normalizedNumber = rawNumber.replace(',', '.')

        val value = normalizedNumber.toDoubleOrNull()
            ?: throw IllegalArgumentException("Invalid number in distance: '$rawNumber' from '$input'.")

        val unitKey = rawUnit.lowercase()

        // Metric units (use existing conversion logic)
        metricUnits[unitKey]?.let { unit ->
            return value.toDistance(unit)
        }

        // Imperial units (convert to millimeters with constants)
        imperialUnits[unitKey]?.let { imperial ->
            val mm = kotlin.math.round(value * imperial.mmPerUnit)
            return Distance(mm.toLong())
        }

        // Unknown unit
        val supported = (metricUnits.keys + imperialUnits.keys)
            .sorted()
            .joinToString(", ")
        throw IllegalArgumentException("Unknown distance unit: '$rawUnit'. Supported units: $supported")
    }
}
