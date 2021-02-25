data class Car(
    val streets: List<Street> = mutableListOf(),
    var relativeTime: Int = 0,
    var finished: Int? = null
) {
    val totalTimeWithoutWait = streets.map { it.length }.sum()
}

data class Street(
    val intersectionStart: Int,
    val intersectionEnd: Int,
    val name: String,
    val length: Int,
    var queue: List<Car> = mutableListOf(),
)

data class ScheduleEntry(
    val street: String,
    var time: Int
) {
    constructor(street: Street, time: Int) : this(street.name, time)
}

data class Intersection(
    val id: Int,
    var schedule: List<ScheduleEntry> = mutableListOf()
)

data class Simulation(
    val duration: Int,
    val bonus: Int,
    var intersections: MutableList<Intersection>,
    val streets: MutableList<Street>,
    val cars: MutableList<Car>,
    var t: Int = 0
)