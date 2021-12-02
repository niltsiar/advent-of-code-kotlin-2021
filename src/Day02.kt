fun main() {
    fun part1(input: List<String>): Int {
        val inputs = input.map { it.split(" ") }
            .map { it[0] to it[1].toInt() }
            .groupBy({ it.first }, { it.second })
            .map { it.key to it.value.sum() }
            .toMap()

        val forward = inputs["forward"] ?: return 0
        val up = inputs["up"] ?: return 0
        val down = inputs["down"] ?: return 0

        return forward * (down - up)
    }

    fun part2(input: List<String>): Int {
        val inputs = input.map { it.split(" ") }
            .map { it[0] to it[1].toInt() }

        val results = inputs.fold(Data(0, 0, 0)) { acc, pair ->
            when (pair.first) {
                "forward" -> acc.copy(forward = acc.forward + pair.second, depth = acc.depth + acc.aim * pair.second)
                "up" -> acc.copy(aim = acc.aim - pair.second)
                "down" -> acc.copy(aim = acc.aim + pair.second)
                else -> acc
            }
        }

        return results.forward * results.depth
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

data class Data(val forward: Int, val aim: Int, val depth: Int)
