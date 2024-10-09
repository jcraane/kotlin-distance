import dev.jamiecraane.kotlindistance.distance.*
import dev.jamiecraane.kotlindistance.distance.Distance.Companion.kilometers
import dev.jamiecraane.kotlindistance.distance.Distance.Companion.meters
import kotlin.test.*

class DistanceIteratorTest {

    @Test
    fun testPositiveStep() {
        val start = 1.kilometers
        val end = 3.kilometers
        val step = 500.meters
        val iterator = DistanceIterator(start, end, step)

        val expected = listOf(
            1.kilometers,
            1.5.kilometers,
            2.kilometers,
            2.5.kilometers,
            3.kilometers
        )

        val actual = iterator.asSequence().toList()
        assertEquals(expected, actual, "DistanceIterator did not produce expected values for positive step")
    }

    @Test
    fun testNegativeStep() {
        val start = 3.kilometers
        val end = 1.kilometers
        val step = (-500).meters
        val iterator = DistanceIterator(start, end, step)

        val expected = listOf(
            3.kilometers,
            2.5.kilometers,
            2.kilometers,
            1.5.kilometers,
            1.kilometers
        )

        val actual = iterator.asSequence().toList()
        assertEquals(expected, actual, "DistanceIterator did not produce expected values for negative step")
    }

    @Test
    fun testZeroStepThrowsException() {
        val start = 1.kilometers
        val end = 3.kilometers
        val step = 0.meters
        val exception = assertFailsWith<IllegalArgumentException> {
            DistanceProgression(start, end, step)
        }

        assertEquals("Step distance must not be zero", exception.message, "Expected exception not thrown for zero step")
    }
}
