package solvers

import ScheduleEntry
import TrafficChallenge
import com.github.bluemasters.hashcode.Challenge
import com.github.bluemasters.hashcode.Solver
import kotlin.random.Random

object RandomSolver : Solver {
    override fun solve(challenge: Challenge) {
        check(challenge is TrafficChallenge) { "oops" }

        val sim = challenge.simulation

        val usedStreets = sim.cars.flatMap { it.streets.dropLast(1) }.toSet()

        usedStreets
            .groupBy { it.intersectionEnd }
            .mapValues { (_, streets) -> streets.shuffled() }
            .forEach { (intersectionId, streets) ->
                sim.intersections[intersectionId].schedule = streets.map {
                    ScheduleEntry(it.name, Random.nextInt(1, 3))
                }.toMutableList()
            }
    }

}