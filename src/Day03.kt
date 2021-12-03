fun main() {
    fun part1(input: List<String>): Int {
        val inputs = input.map {
            it.toCharArray().mapIndexed { index, c -> index to c }
        }.flatten()
            .groupBy({ it.first }) { it.second }
            .mapValues { it.value.groupingBy { it }.eachCount() }

        val gamma = inputs.mapValues { it.value.entries.toList().sortedBy { it.value }.first().key }
            .entries
            .toList()
            .sortedBy { it.key }
            .map { it.value }
            .joinToString("")
            .toInt(2)

        val epsilon = inputs.mapValues { it.value.entries.toList().sortedByDescending { it.value }.first().key }
            .entries
            .toList()
            .sortedBy { it.key }
            .map { it.value }
            .joinToString("")
            .toInt(2)


        println("Gamma: $gamma")
        println("Epsilon: $epsilon")

        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        tailrec fun reduce(index: Int, results: List<List<Char>>, isLookingForMax: Boolean): List<Char> {
            println(results)

            if (results.size == 1) {
                return results.first()
            }

            val groups = results.groupingBy { it[index] }
                .eachCount()
                .onEach { println(it) }

            val minMax = groups.values.let {
                if (isLookingForMax) {
                    it.maxOrNull()
                } else {
                    it.minOrNull()
                }
            } ?: return results.first()

            val selection = groups.filter { it.value == minMax }

            val mostRepeatedDigit = if (selection.size > 1) {
                if (isLookingForMax) {
                    '1'
                } else {
                    '0'
                }
            } else {
                selection.keys.toList()[0]
            }

            println()

            val newResults = results.filter { it[index] == mostRepeatedDigit }

            return reduce(index + 1, newResults, isLookingForMax)
        }

        val inputs = input.map {
            it.toCharArray().toList()
        }

        val oxygenLevel = reduce(0, inputs, true).joinToString("")
        val co2 = reduce(0, inputs, false).joinToString("")

        println(oxygenLevel)
        println(co2)

        return oxygenLevel.toInt(2) * co2.toInt(2)
    }


// test if implementation meets criteria from the description, like:
// val testInput = readInput("Day01_test")
// check(part1(testInput) == 1)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
