fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input[0].split(',')
            .map { it.toInt() }

        println(numbers)

        val boardNumbers = input.subList(1, input.size)
            .filter { it.isNotBlank() }
            .map {
                it.split(' ')
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }
            }

        val boards = boardNumbers.windowed(5, 5)
            .map { Board(it) }

        numbers.forEach { n ->
            boards.forEach {
                val isCompleted = it.addNumber(n)
                if (isCompleted) {
                    println(n)
                    println(it)
                    return n * it.sumUnmarkedNumbers()
                }
            }
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        val numbers = input[0].split(',')
            .map { it.toInt() }

        println(numbers)

        val boardNumbers = input.subList(1, input.size)
            .filter { it.isNotBlank() }
            .map {
                it.split(' ')
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }
            }

        val boards = boardNumbers.windowed(5, 5)
            .map { Board(it) }
            .toMutableList()

        numbers.forEach { n ->
            val tempBoards = boards.toList()
            tempBoards.forEach {
                val isCompleted = it.addNumber(n)
                if (isCompleted) {
                    boards.remove(it)
                }
                if (isCompleted && boards.isEmpty()) {
                    return n * it.sumUnmarkedNumbers()
                }
            }
        }
        return 0
    }


// test if implementation meets criteria from the description, like:
// val testInput = readInput("Day01_test")
// check(part1(testInput) == 1)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

data class Board(
    val numbers: List<List<Int>>,
    val rows: MutableList<MutableMap<Int, Boolean>> = mutableListOf(),
    val columns: MutableList<MutableMap<Int, Boolean>> = mutableListOf(),
) {

    init {
        numbers.forEach { ints ->
            ints.forEachIndexed { index, i ->
                if (columns.size == index) {
                    columns.add(index, mutableMapOf())
                }
                columns[index][i] = false
            }
            rows.add(ints.associateWith { false }.toMutableMap())
        }
    }

    fun addNumber(n: Int): Boolean {
        rows.markNumber(n)
        columns.markNumber(n)

        return isCompleted()
    }

    fun isCompleted() = rows.isCompleted() || columns.isCompleted()

    private fun MutableList<MutableMap<Int, Boolean>>.markNumber(n: Int) {
        forEach {
            if (it[n] != null) {
                it[n] = true
            }
        }
    }

    private fun List<Map<Int, Boolean>>.isCompleted(): Boolean {
        return fold(false) { acc, map ->
            acc || map.values.fold(true) { mapAcc, b -> mapAcc && b }
        }
    }

    fun sumUnmarkedNumbers(): Int {
        return rows.sumUnmarkedNumbers()
    }

    private fun List<Map<Int, Boolean>>.sumUnmarkedNumbers(): Int {
        return fold(0) { acc, map ->
            acc + map.entries.filter { !it.value }.sumOf { it.key }
        }
    }
}
