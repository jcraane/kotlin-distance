package dev.jamiecraane.kotlindistance.distance

import dev.jamiecraane.kotlindistance.distance.Distance.Companion.kilometers
import dev.jamiecraane.kotlindistance.distance.Distance.Companion.meters
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DistanceKtTest {
    @Test
    fun createDistance() {
        assertEquals(2000, 2000.meters.inWholeMeters)
        assertEquals(2, 2000.meters.inWholeKilometers)
        assertEquals(1500, 1.5.kilometers.inWholeMeters)
        assertEquals(150, 150.0.meters.inWholeMeters)
        assertEquals(2000, 2000L.meters.inWholeMeters)
        assertEquals(2, 2000L.meters.inWholeKilometers)
    }

    @Test
    fun plus() {
        assertEquals(5.kilometers, 2.kilometers + 3.kilometers)
        assertEquals(1500.meters, 1.kilometers + 500.meters)
    }

    @Test
    fun minus() {
        assertEquals(1.kilometers, 3.kilometers - 2.kilometers)
        assertEquals(500.meters, 1.kilometers - 500.meters)
    }

    @Test
    fun zero() {
        assertEquals(0, Distance.ZERO.inWholeMeters)
        assertEquals(0, Distance.ZERO.inWholeKilometers)
    }

    @Test
    fun compare() {
        assertTrue(500.meters > 250.meters)
        assertEquals(500.meters, 500.meters)
        assertTrue(5.kilometers > 2.kilometers)
        assertFalse(5.kilometers < 2.kilometers)
        assertTrue(1.kilometers < 2.kilometers)
    }

    @Test
    fun withinRange() {
        assertTrue(100.meters in 50.meters..150.meters)
        assertTrue(50.meters in 50.meters..150.meters)
        assertTrue(150.meters in 50.meters..150.meters)
        assertFalse(40.meters in 50.meters..150.meters)
        assertFalse(151.meters in 50.meters..150.meters)
    }

    @Test
    fun createRange() {
        val expected = listOf(
            100.meters,
            110.meters,
            120.meters,
            130.meters,
            140.meters,
            150.meters,
            160.meters,
            170.meters,
            180.meters,
            190.meters,
            200.meters,
        )

        for ((index, distance) in (100.meters..200.meters step 10.meters).withIndex()) {
            assertEquals(expected[index], distance)
        }
    }

    @Test
    fun abs() {
        assertEquals(20.meters, (-20).meters.abs())
    }
}
