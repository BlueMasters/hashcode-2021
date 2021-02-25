object Simulator {

    fun reset(simulation: Simulation): Simulation {

        simulation.streets.forEach { s -> s.queue = mutableListOf()}

        for(car in simulation.cars) {
            car.relativeTime = 0
            car.crtStreet = 0
            car.streets[0].queue.add(car)
            car.finished = null
        }
        return simulation
    }

    fun run(simulation: Simulation): Long {

        reset(simulation)

        for(step in 0 until simulation.duration) {

            simulation.t = step

            for(intersection in simulation.intersections ) {
                intersection.turn(step)?.let { name ->
                    simulation.streetMap[name]?.let { street ->
                        street.queue.firstOrNull()?.let { car ->
                            if(car.relativeTime == 0) {

                                if(car.crtStreet == car.streets.size - 2) {
                                    car.finished = step + car.streets.last().length
                                    if(car.finished!! >= simulation.duration) {
                                        car.finished = null
                                    }
                                } else {
                                    car.crtStreet++
                                    val nextStreet = car.streets[car.crtStreet]
                                    nextStreet.queue.add(car)
                                    car.relativeTime = nextStreet.length
                                }

                                street.queue.removeFirst()
                            }
                        }
                    }
                }
            }

            simulation.cars.forEach { car -> car.relativeTime -= 1 ; if (car.relativeTime < 0) car.relativeTime = 0}

        }

        var score = 0L

        simulation.cars.map { car ->
            if(car.finished != null) {
                score += simulation.bonus
                score += (simulation.duration - car.finished!!)
            }
        }
        return score
    }
}