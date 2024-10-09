package dev.jamiecraane.kotlindistance.distance

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToLong

/**
 * @property orderLowerIsLarger Used for deterministic ordering independent of the order of values in the enumeration.
 */
enum class DistanceUnit(private val orderLowerIsLarger: Int) {
    KILOMETERS(1),
    HECTOMETERS(2),
    DECAMETERS(3),
    METERS(4),
    DECIMETERS(5),
    CENTIMETERS(6),
    MILLIMETERS(7);

    /**
     * Converts value in 'this' unit to the specified 'other' unit.
     *
     * @param value The value to convert
     * @param target The target DistanceUnit to convert to
     */
    fun convert(value: Double, target: DistanceUnit): Long {
        if (this == target) return value.roundToLong()

        val firstIndex = largestFirst.indexOf(this)
        val lastIndex = largestFirst.indexOf(target)

        val (numberOfZeros, sign) = determineNumberOfStepsAndSignBetweenUnits(firstIndex, lastIndex)
        val factor = calculateConversionFactor(numberOfZeros, sign)
        return doConvert(factor, value)
    }

    private fun determineNumberOfStepsAndSignBetweenUnits(
        firstIndex: Int,
        lastIndex: Int,
    ) = if (firstIndex > lastIndex) {
        firstIndex - lastIndex to -1
    } else {
        lastIndex - firstIndex to 1
    }

    private fun calculateConversionFactor(numberOfZeros: Int, inversion: Int) =
        10.0.pow(numberOfZeros.toDouble()).toLong() * inversion

    private fun doConvert(factor: Long, value: Double) = (
        if (factor < 0) {
            abs(value / factor)
        } else {
            value * factor
        }.roundToLong()
        )

    companion object {
        /**
         * Ordering where KILOMETERS is first and MILLIMETERS is last.
         */
        private val largestFirst = entries.sortedBy { it.orderLowerIsLarger }
    }
}
