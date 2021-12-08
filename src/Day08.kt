fun main() {
    fun part1(input: List<String>): Int {
        val inputs = input.map {
            it.split(" | ")[1]
                .split(' ')
        }

        val validNumberOfSegments = listOf(2, 3, 4, 7)

        val count = inputs.flatten()
            .count { validNumberOfSegments.contains(it.count()) }

        return count
    }

    fun part2(input: List<String>): Int {
        fun List<List<String>>.find(size: Int, predicate: (List<String>) -> Boolean = { true }): List<String> {
            return filter { it.size == size }.single { predicate(it) }
        }

        return input.sumOf { line ->
            val (digits, number) = line.split(" | ").map { it.split(" ").map { it.chunked(1) } }

            println(digits)
            println(number)

            val wiring = mutableMapOf<Int, List<String>>()
            wiring[1] = digits.find(2)
            wiring[4] = digits.find(4)
            wiring[7] = digits.find(3)
            wiring[8] = digits.find(7)
            wiring[2] = digits.find(5) { it.filter { it in wiring.getValue(4) }.size == 2 }
            wiring[3] = digits.find(5) { it.containsAll(wiring.getValue(1)) }
            wiring[5] = digits.find(5) { it !in listOf(wiring.getValue(2), wiring.getValue(3)) }
            wiring[6] = digits.find(6) { !it.containsAll(wiring.getValue(1)) }
            wiring[9] = digits.find(6) { it.containsAll(wiring.getValue(4)) }
            wiring[0] = digits.find(6) { it !in listOf(wiring.getValue(6), wiring.getValue(9)) }
            val map = wiring.entries.associate { (k, v) -> v.sorted() to k }
            number.map { map.getValue(it.sorted()) }.joinToString("").toInt()
        }
    }


// test if implementation meets criteria from the description, like:
// val testInput = readInput("Day01_test")
// check(part1(testInput) == 1)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
