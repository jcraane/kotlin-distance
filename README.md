# Kotlin Distance Library

A Kotlin library that provides an intuitive and type-safe way to handle distances. Inspired by kotlin.time.Duration, the
library supports various distance units, arithmetic operations, and range progressions with custom step sizes.

## Features

Distance Representation: Create distances using several factory functions.
Arithmetic Operations: Supports addition, subtraction, and comparison of distances.
Range Progressions: Iterate over ranges of distances with custom steps, including support for both positive and negative
steps.
Inline Classes: Efficient, type-safe representation of distances using Kotlin's inline classes.

## Installation

Add the following dependency to your build.gradle.kts:

```kotlin
dependencies {
    implementation("com.github.username:distance-library:1.0.0")
}
```

```groovy
dependencies {
    implementation 'com.github.username:distance-library:1.0.0'
}
```

# Usage

## Creating Distances

```kotlin
import dev.jamiecraane.kotlindistance.distance.Distance.Companion.kilometers
import dev.jamiecraane.kotlindistance.distance.Distance.Companion.meters

val distance1 = 10.kilometers
val distance2 = 500.meters
```

## Arithmetic Operations

```kotlin
val totalDistance = distance1 + distance2
val difference = distance1 - distance2

println("Total distance in meters: ${totalDistance.inWholeMeters}")
println("Difference in kilometers: ${difference.inWholeKilometers}")
```

## Comparing Distances

```kotlin
if (distance1 > distance2) {
    println("Distance1 is greater than Distance2")
}
```

## Distance Progression

```kotlin
val range = 1.kilometers..5.kilometers step 500.meters
for (distance in range) {
    println(distance)
}

val reverseRange = 5.kilometers downTo 1.kilometers step 500.meters
for (distance in reverseRange) {
    println(distance)
}
```

## Acknowledgments
- Inspired by Kotlin's Duration class.

## Roadmap

### Support for Additional Units
   Imperial Units: Add support for miles, yards, feet, and inches.
   Astronomical Units: For niche applications, support for units like light-years, astronomical units (AU), parsecs, etc.

### Conversion Between Units
   Provide functions to easily convert between different units of distance. For instance:

```kotlin
  val distanceInMiles = distanceInKilometers.toMiles()
  val distanceInFeet = distanceInMeters.toFeet()
``` 

### Formatting and String Representation

```kotlin
val formattedDistance = distance.toHumanReadableString() // "10 kilometers" or "10 km"
```

### Parsing from Strings

```kotlin
val distance: Distance = Distance.parse("15 km")
```

### Serialization and Deserialization

```kotlin
@Serializable
  data class Journey(val distance: Distance)
```

### Advanced Mathematical Operation

```kotlin
val scaledDistance = distance.scale(2.5)
```

### Integration with Geospatial Libraries

### Extending Range Operations
   - Advanced range operations such as sub-ranges and partial iterations.

```kotlin
val subRange = range.subRange(2.kilometers, 4.kilometers)
```

### Make it a multiplatform library
