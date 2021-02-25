package solvers

import ScheduleEntry
import TrafficChallenge
import com.github.bluemasters.hashcode.Challenge
import com.github.bluemasters.hashcode.Solver
import kotlin.random.Random

object StarSolver : Solver {
    override fun solve(challenge: Challenge) {
        check(challenge is TrafficChallenge) { "oops" }

        val sim = challenge.simulation

        sim.streets.forEach { s -> s.queue = mutableListOf()}

        for(car in sim.cars) {
            car.relativeTime = 0
            car.crtStreet = 0
            car.streets[0].queue.add(car)
            car.finished = null
        }

        val middle = sim.intersections.find { it.id == 499 }!!

        sim.intersections.forEach { intersection ->
            if (intersection.id != 499) {
                val streets = sim.incomingStreets(intersection)
                for (s in streets) {
                    intersection.schedule.add(ScheduleEntry(s.name, 1))
                }
                intersection.schedule.shuffle()
            }
        }

        val incomingMiddle = sim.incomingStreets(middle)
        for (s in incomingMiddle) {
            middle.schedule.add(ScheduleEntry(s.name, 1))
        }
        middle.schedule.sortBy { scheduleEntry ->
            val street = sim.streetMap[scheduleEntry.street]!!
            street.length
        }
        middle.schedule.forEach {scheduleEntry ->
            val street = sim.streetMap[scheduleEntry.street]!!
            scheduleEntry.time = street.queue.size
        }
    }
}