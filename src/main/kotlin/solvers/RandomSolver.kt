package solvers

import ScheduleEntry
import TrafficChallenge
import com.github.bluemasters.hashcode.Challenge
import com.github.bluemasters.hashcode.Solver

object RandomSolver : Solver {
    override fun solve(challenge: Challenge) {
        check(challenge is TrafficChallenge) { "oops" }

        val sim = challenge.simulation

        sim.streets
            .groupBy { it.intersectionEnd }
            .mapValues { (intersectionId, streets) -> streets.shuffled() }
            .forEach { (intersectionId, streets) ->
                sim.intersections[intersectionId].schedule = streets.map {
                    ScheduleEntry(it.name, 1)
                }
            }
    }

}