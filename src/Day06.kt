import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        val initialState = input[0].split(',').map { it.toInt() }
        val newState: List<Int>
        val endDay = 80
        val time = measureTimeMillis {
            newState = calculateState(initialState, 1, endDay)
        }
        println("calculateState with $endDay iterations takes $time millis")
        return newState.size
    }

    fun part2(input: List<String>): Long {
        val initialState = input[0].split(',').map { it.toInt() }
            .groupBy { it }
            .map { it.key to it.value.count().toLong() }
            .toMap()
        val newState: Map<Int, Long>
        val sum: Long
        val endDay = 256
        val timeCalculateStateImproved = measureNanoTime {
            newState = calculateStateImproved(initialState, 1, endDay)
        }
        val timeSum = measureNanoTime {
            sum = newState.values.sum()
        }
        val arrayState = LongArray(9) { 0 }
        input[0].split(',').map { it.toInt() }.forEach {
            arrayState[it] = arrayState[it] + 1
        }
        val newState2: LongArray
        val sum2: Long
        val timeCalculateStateImproved2 = measureNanoTime {
            newState2 = calculateStateImproved2(arrayState, 1, endDay)
        }
        val timeSum2 = measureNanoTime {
            sum2 = newState2.sum()
        }
        println("Is same result?: ${sum == sum2}")
        println("calculateStateImproved with $endDay iterations takes $timeCalculateStateImproved nanos")
        println("calculateStateImproved2 with $endDay iterations takes $timeCalculateStateImproved2 nanos")
        println("sum values of Map<Int, Long> takes $timeSum nanos")
        println("sum LongArray takes $timeSum2 nanos")
        println("Differences between using Map<Int, Long> and LongArray ${timeCalculateStateImproved + timeSum - timeCalculateStateImproved2 - timeSum2} nanos")
        return sum2
    }


// test if implementation meets criteria from the description, like:
// val testInput = readInput("Day01_test")
// check(part1(testInput) == 1)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

tailrec fun calculateState(state: List<Int>, initialDay: Int, lastDay: Int): List<Int> {
    val newState = List(state.size + state.count { it == 0 }) { index ->
        if (index >= state.size) {
            8
        } else {
            val tempState = state[index] - 1
            if (tempState == -1) {
                6
            } else {
                tempState
            }
        }
    }

    return if (initialDay == lastDay) {
        newState
    } else {
        calculateState(newState, initialDay + 1, lastDay)
    }
}

tailrec fun calculateStateImproved(state: Map<Int, Long>, initialDay: Int, lastDay: Int): Map<Int, Long> {
    val tempZero = state[0] ?: 0
    val newMap = mutableMapOf<Int, Long>()
    (1..8).forEach { index ->
        newMap[index - 1] = state[index] ?: 0
    }
    newMap[8] = tempZero
    newMap[6] = (newMap[6] ?: 0) + tempZero

    return if (initialDay == lastDay) {
        newMap
    } else {
        calculateStateImproved(newMap, initialDay + 1, lastDay)
    }
}

tailrec fun calculateStateImproved2(state: LongArray, initialDay: Int, lastDay: Int): LongArray {
    val tempZero = state[0] ?: 0
    val newState = LongArray(9) { 0 }
    (1..8).forEach { index ->
        newState[index - 1] = state[index] ?: 0
    }
    newState[8] = tempZero
    newState[6] = (newState[6] ?: 0) + tempZero

    return if (initialDay == lastDay) {
        newState
    } else {
        calculateStateImproved2(newState, initialDay + 1, lastDay)
    }
}
