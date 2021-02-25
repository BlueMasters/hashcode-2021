data class Car(
    val streets: MutableList<Street> = mutableListOf(),
    var crtStreet : Int = 0,
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
    var queue: MutableList<Car> = mutableListOf(),
)

data class ScheduleEntry(
    val street: String,
    var time: Int
) {
    constructor(street: Street, time: Int) : this(street.name, time)
}

data class Intersection(
    val id: Int,
    var schedule: MutableList<ScheduleEntry> = mutableListOf()
) {
    fun turn(crtTime : Int) : String? {
        val totalScheduleTime = schedule.map { it.time }.sum()
        val reminder = crtTime % totalScheduleTime
        var acc = 0
        for(scheduleEntry in schedule) {
            if(reminder < scheduleEntry.time + acc) {
                return scheduleEntry.street
            }
            acc += scheduleEntry.time
        }
        return null
    }
}

data class Simulation(
    val duration: Int,
    val bonus: Int,
    var intersections: MutableList<Intersection>,
    val streets: MutableList<Street>,
    val cars: MutableList<Car>,
    var t: Int = 0
) {
    val streetMap : Map<String, Street> = streets.map { s -> Pair(s.name, s) }.toMap()
}