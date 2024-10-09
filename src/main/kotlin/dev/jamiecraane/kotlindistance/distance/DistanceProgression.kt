package dev.jamiecraane.kotlindistance.distance

import dev.jamiecraane.kotlindistance.distance.Distance.Companion.meters

class DistanceProgression(
    override val start: Distance,
    override val endInclusive: Distance,
    private val step: Distance = 1.meters,
) : Iterable<Distance>, ClosedRange<Distance> {
    init {
        require(step.rawValue != 0L) { "Step distance must not be zero" }
    }

    override fun iterator(): Iterator<Distance> = DistanceIterator(start, endInclusive, step)

    infix fun step(distance: Distance) = DistanceProgression(start, endInclusive, distance)
}
