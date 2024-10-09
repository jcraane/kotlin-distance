package dev.jamiecraane.kotlindistance.distance

class DistanceIterator(
    start: Distance,
    private val endInclusive: Distance,
    private val step: Distance
) : Iterator<Distance> {
    private var current = start

    override fun hasNext() = if (step.rawValue > 0) {
        current <= endInclusive
    } else {
        current >= endInclusive
    }

    override fun next(): Distance {
        if (!hasNext()) throw NoSuchElementException()
        val next = current
        current += step
        return next
    }
}
