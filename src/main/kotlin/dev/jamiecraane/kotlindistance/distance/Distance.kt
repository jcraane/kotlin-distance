package dev.jamiecraane.kotlindistance.distance


/**
 * A value class representing a distance. Internally, the distance is stored in millimeters.
 *
 * Distance is inspired by [kotlin.time.Duration]
 *
 * @property rawValue The raw value of the distance in millimeters.
 */
@JvmInline
value class Distance internal constructor(val rawValue: Long) : Comparable<Distance> {
    val inWholeMeters: Long
        get() = toLong(DistanceUnit.METERS)

    val inWholeKilometers: Long
        get() = toLong(DistanceUnit.KILOMETERS)

    override fun compareTo(other: Distance) = this.rawValue.compareTo(other.rawValue)

    private fun toLong(targetUnit: DistanceUnit) =
        convertDistanceUnit(rawValue.toDouble(), DistanceUnit.MILLIMETERS, targetUnit)

    operator fun plus(other: Distance) = Distance(rawValue + other.rawValue)
    operator fun minus(other: Distance) = Distance(rawValue - other.rawValue)
    operator fun rangeTo(other: Distance) = DistanceProgression(this, other)

    fun abs(): Distance = Distance(kotlin.math.abs(rawValue))

    override fun toString() = "Distance(rawValue=$rawValue)"

    companion object {
        val ZERO: Distance = Distance(0L)

        inline val Long.meters get() = this.toDistance(DistanceUnit.METERS)
        inline val Long.kilometers get() = this.toDistance(DistanceUnit.KILOMETERS)
        inline val Int.meters get() = this.toDistance(DistanceUnit.METERS)
        inline val Int.kilometers get() = this.toDistance(DistanceUnit.KILOMETERS)
        inline val Double.kilometers get() = this.toDistance(DistanceUnit.KILOMETERS)
        inline val Double.meters get() = this.toDistance(DistanceUnit.METERS)
    }
}

fun Number.toDistance(unit: DistanceUnit): Distance {
    val inMillimeters = convertDistanceUnit(this.toDouble(), unit, DistanceUnit.MILLIMETERS)
    return Distance(inMillimeters)
}

private fun convertDistanceUnit(value: Double, unit: DistanceUnit, target: DistanceUnit) =
    unit.convert(value, target)
