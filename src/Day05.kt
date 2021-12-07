import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        val processedInput = input.map { it.split(" -> ") }
            .map {
                it.map {
                    it.split(',')
                        .map { it.toInt() }
                }
                    .map { it[0] to it[1] }
            }
            .map { it[0] to it[1] }
            .filter {
                it.first.first == it.second.first || it.first.second == it.second.second
            }

        val map: MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()

        processedInput.forEach { inputs ->
            val isHorizontal = inputs.first.first == inputs.second.first
            if (isHorizontal) {
                val row = map[inputs.first.first] ?: map.computeIfAbsent(inputs.first.first) {
                    mutableMapOf()
                }
                (min(inputs.first.second, inputs.second.second)..max(
                    inputs.first.second,
                    inputs.second.second
                )).forEach { n ->
                    row[n] = (row[n] ?: 0) + 1
                }
            } else {
                (min(inputs.first.first, inputs.second.first)..max(
                    inputs.first.first,
                    inputs.second.first
                )).forEach { n ->
                    val column = map[n] ?: map.computeIfAbsent(n) {
                        mutableMapOf()
                    }
                    column[inputs.first.second] = (column[inputs.first.second] ?: 0) + 1
                }
            }
        }

        val points = map.values.map { it.values.filter { it >= 2 } }.flatten().count()

        println(processedInput)
        println(map)

        return points
    }

    fun part2(input: List<String>): Int {
        val processedInput = input.map { it.split(" -> ") }
            .map {
                it.map {
                    it.split(',')
                        .map { it.toInt() }
                }
                    .map { it[0] to it[1] }
            }
            .map { it[0] to it[1] }

        val map: MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()

        processedInput.forEach { inputs ->
            if (inputs.first.first == inputs.second.first) {
                val row = map[inputs.first.first] ?: map.computeIfAbsent(inputs.first.first) {
                    mutableMapOf()
                }
                (min(inputs.first.second, inputs.second.second)..max(
                    inputs.first.second,
                    inputs.second.second
                )).forEach { n ->
                    row[n] = (row[n] ?: 0) + 1
                }
            } else if (inputs.first.second == inputs.second.second) {
                (min(inputs.first.first, inputs.second.first)..max(
                    inputs.first.first,
                    inputs.second.first
                )).forEach { n ->
                    val column = map[n] ?: map.computeIfAbsent(n) {
                        mutableMapOf()
                    }
                    column[inputs.first.second] = (column[inputs.first.second] ?: 0) + 1
                }
            } else {
                val rowIndex = if (inputs.first.first < inputs.second.first) {
                    inputs.first.first..inputs.second.first
                } else {
                    inputs.first.first downTo inputs.second.first
                }
                val columnIndex = if (inputs.first.second < inputs.second.second) {
                    inputs.first.second..inputs.second.second
                } else {
                    inputs.first.second downTo inputs.second.second
                }
                rowIndex.forEachIndexed { index, n ->
                    val row = map[n] ?: map.computeIfAbsent(n) {
                        mutableMapOf()
                    }
                    row[columnIndex.elementAt(index)] = (row[columnIndex.elementAt(index)] ?: 0) + 1
                }
            }
        }

        val points = map.values.map { it.values.filter { it >= 2 } }.flatten().count()

        println(processedInput)
        println(map)

        return points
    }


// test if implementation meets criteria from the description, like:
// val testInput = readInput("Day01_test")
// check(part1(testInput) == 1)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
