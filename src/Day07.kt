import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {

        val positions = input[0].split(',').map { it.toInt() }

        val positionsRange = (positions.minOrNull() ?: 0)..(positions.maxOrNull() ?: 0)

        val fuelCosts = List(positionsRange.last - positionsRange.first) { index ->
            positions.fold(0) { acc, n ->
                acc + abs(index - n)
            }
        }

        println(fuelCosts)

        return fuelCosts.minOrNull() ?: 0
    }

    fun part2(input: List<String>): Int {
        val positions = input[0].split(',').map { it.toInt() }

        val positionsRange = (positions.minOrNull() ?: 0)..(positions.maxOrNull() ?: 0)

        val fuelCosts = List(positionsRange.last - positionsRange.first) { index ->
            positions.fold(0) { acc, n ->
                acc + calculateFuelCosts(abs(index - n))
            }
        }

        println(fuelCosts)

        return fuelCosts.minOrNull() ?: 0
    }


// test if implementation meets criteria from the description, like:
// val testInput = readInput("Day01_test")
// check(part1(testInput) == 1)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

private val fuelCostsMap: MutableMap<Int, Int> = mutableMapOf<Int, Int>().apply {
    put(0, 0)
    put(1, 1)
}

private fun calculateFuelCosts(n: Int): Int {
    return fuelCostsMap[n] ?: run {
        val result = n + calculateFuelCosts(n - 1)
        fuelCostsMap[n] = result
        result
    }
}
