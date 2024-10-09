package dev.jamiecraane.kotlindistance.distance

import kotlin.test.Test
import kotlin.test.assertEquals

class DistanceUnitTest {
    @Test
    fun convert() {
        assertEquals(2, DistanceUnit.METERS.convert(2000.0, DistanceUnit.KILOMETERS))
        assertEquals(2000, DistanceUnit.METERS.convert(2.0, DistanceUnit.MILLIMETERS))
    }
}
