package solvers

import TrafficChallenge
import com.github.bluemasters.hashcode.Challenge
import com.github.bluemasters.hashcode.Solver

object StatsSolver : Solver {
    override fun solve(challenge: Challenge) {
        check(challenge is TrafficChallenge) { "oops" }
        println("\n===============")
        println(challenge.inFile)
        println("===============")

        val sim = challenge.simulation

        val usedStreets = sim.cars.flatMap {
            it.streets.dropLast(1)
        }

        val unusedStreets = sim.streets.map { it.name } - usedStreets.map { it.name }
        println("Unused streets: ${unusedStreets.size}")
        println()
        println("Street Use:")
        stats(usedStreets.groupBy { it.name }.map { it.value.size })
        println()
        println("Cars total time:")
        stats(sim.cars.map { it.totalTimeWithoutWait })
        println()
        println("Cars total intersections:")
        stats(sim.cars.map { it.streets.size })
    }

    fun stats(lst: List<Int>) {
        val sum = lst.sum()
        val cnt = lst.size
        val sorted = lst.sorted()
        val median = sorted.get(cnt / 2)

        println(" cnt: $cnt")
        println(" sum: $sum")
        println(" min: ${sorted.first()}")
        println(" max: ${sorted.last()}")
        println(" avg: ${sum / cnt}")
        println(" median: $median")
    }
}