package dev.jamiecraane.kotlindistance.distance

import kotlin.math.abs
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DistanceParserTest {
    @Test
    fun parsesMetric() {
        assertEquals(5.0.toDistance(DistanceUnit.KILOMETERS), DistanceParser.parse("5km"))
        assertEquals(5.0.toDistance(DistanceUnit.KILOMETERS), DistanceParser.parse("5 km"))
        assertEquals(5.0.toDistance(DistanceUnit.KILOMETERS), DistanceParser.parse("5.0 kilometers"))
        assertEquals(100.0.toDistance(DistanceUnit.METERS), DistanceParser.parse("100 m"))
        assertEquals(150.0.toDistance(DistanceUnit.CENTIMETERS), DistanceParser.parse("150 cm"))
        assertEquals(7.0.toDistance(DistanceUnit.MILLIMETERS), DistanceParser.parse("7mm"))
        assertEquals(1.5.toDistance(DistanceUnit.KILOMETERS), DistanceParser.parse("1,5 km"))
    }

    @Test
    fun parsesImperial() {
        // miles
        val mi = DistanceParser.parse("3.2 miles")
        val expectedMiMm = kotlin.math.round(3.2 * 1_609_344.0).toLong()
        assertEquals(Distance(expectedMiMm), mi)

        // feet
        assertEquals(Distance(304.8.roundToLong()), DistanceParser.parse("1 ft"))
        assertEquals(Distance((12 * 25.4).roundToLong()), DistanceParser.parse("12 in"))
        assertEquals(Distance((2 * 914.4).roundToLong()), DistanceParser.parse("2 yards"))
        assertEquals(Distance(25.4.roundToLong()), DistanceParser.parse("1INCH"))
    }

    @Test
    fun validationErrors() {
        assertFailsWith<IllegalArgumentException> { DistanceParser.parse("") }
        assertFailsWith<IllegalArgumentException> { DistanceParser.parse("15") } // missing unit
        assertFailsWith<IllegalArgumentException> { DistanceParser.parse("abc km") }
        val ex = assertFailsWith<IllegalArgumentException> { DistanceParser.parse("10 qq") }
        // Ensure message mentions unknown unit
        assert(ex.message!!.contains("Unknown distance unit"))
    }

    @Test
    fun fuzzAmbiguousSpacingCasing() {
        data class Case(val value: Double, val unit: String, val expectedMm: Long)
        fun expectedMmForMetric(value: Double, unit: DistanceUnit): Long = value.toDistance(unit).rawValue

        val cases = listOf(
            // metric
            Case(5.0, "km", expectedMmForMetric(5.0, DistanceUnit.KILOMETERS)),
            Case(100.0, "m", expectedMmForMetric(100.0, DistanceUnit.METERS)),
            Case(150.0, "cm", expectedMmForMetric(150.0, DistanceUnit.CENTIMETERS)),
            Case(7.0, "mm", expectedMmForMetric(7.0, DistanceUnit.MILLIMETERS)),
            // imperial precalculated
            Case(1.0, "mi", kotlin.math.round(1.0 * 1_609_344.0).toLong()),
            Case(2.0, "yd", kotlin.math.round(2.0 * 914.4).toLong()),
            Case(3.0, "ft", kotlin.math.round(3.0 * 304.8).toLong()),
            Case(4.0, "in", kotlin.math.round(4.0 * 25.4).toLong()),
        )

        val spaces = listOf("", " ", "  ")
        val variants = listOf<(String) -> String>(
            { it.lowercase() },
            { it.uppercase() },
            { it.replaceFirstChar { c -> c.uppercase() } },
        )

        repeat(100) {
            val c = cases.random()
            val s1 = spaces.random()
            val s2 = spaces.random()
            val unitVariant = variants.random().invoke(c.unit)
            val valueStr = if (Random.nextBoolean()) c.value.toString() else c.value.toString().replace('.', ',')
            val input = "$s1$valueStr$s2$unitVariant$s1"
            val parsed = DistanceParser.parse(input)
            assertEquals(Distance(c.expectedMm), parsed, "Failed for input: '$input'")
        }
    }
}

private fun Double.roundToLong(): Long = kotlin.math.round(this).toLong()
