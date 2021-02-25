package solvers

import ScheduleEntry
import TrafficChallenge
import com.github.bluemasters.hashcode.Challenge
import com.github.bluemasters.hashcode.Solver

object TestSolver : Solver {
    override fun solve(challenge: Challenge) {
        check(challenge is TrafficChallenge) { "oops" }

        val sim = challenge.simulation

        sim.intersections[0].schedule = mutableListOf(
            ScheduleEntry(sim.streets[0], 2)
        )

        sim.intersections[1].schedule = mutableListOf(
            ScheduleEntry(sim.streets[2], 2),
            ScheduleEntry(sim.streets[1], 1)
        )

        sim.intersections[2].schedule = mutableListOf(
            ScheduleEntry(sim.streets[4], 1)
        )
    }
}