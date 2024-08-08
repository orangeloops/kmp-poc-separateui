
import Models.Task
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object Task {
    private var random = 1
    private val platform: Platform = getPlatform()
    private var tasks: List<Task> = emptyList()

    fun addHours(first: String, second: String): String{
        var currentMinutes = first.split(":")[1].toInt() + second.split(":")[1].toInt()
        var currentHours = first.split(":")[0].toInt() + second.split(":")[0].toInt()
        if (currentMinutes > 59){
            currentMinutes -= 60
            currentHours += 1
        }
        return if (currentMinutes < 10){
            "$currentHours:0$currentMinutes"
        } else {
            "$currentHours:$currentMinutes"
        }
    }

    fun addTask(title: String, category: String, details: String, hours: String, year: Int, month: Int, day: Int) {
        val date = LocalDateTime(year, month, day, 0, 0)
        val newTask = Task(
            title = title,
            category = category,
            details = details,
            hours = hours,
            date = date,
            inProgress = false
        )
        tasks = tasks + newTask
    }

    fun addPendingTask(title: String, category: String, details: String) {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val newTask = Task(title=title, category = category, details = details, hours = "0:00", date = today, inProgress = true
        )
        tasks = tasks + newTask
    }

    fun calculateDifference(begin: LocalDateTime, ending: LocalDateTime): String {
        val start: Instant = begin.toInstant(TimeZone.UTC)
        val end: Instant = ending.toInstant(TimeZone.UTC)

        val diferencia = end- start
        val minutes = diferencia.inWholeMinutes.mod(60)
        return if (minutes < 10){
            diferencia.inWholeHours.toString() + ":0" + minutes
        } else {
            diferencia.inWholeHours.toString() + ":" + minutes
        }
    }

    fun finishPendingTask(taskId: String){
        val task = tasks.find { it.id == taskId }
        task!!.inProgress = false
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        task!!.hours = calculateDifference(task.date, today)
    }

    fun getTasks(): List<Task> {
        return tasks
    }

    fun getTasks(number: String, month: String): List<Task> {
        var filtered = mutableListOf<Task>()
        for (task : Task in tasks){
            if (task.date.dayOfMonth.toString() == number && task.date.month.name.lowercase() == month.lowercase()){
                filtered.add(task)
            }
        }

        return filtered
    }
    fun getClients(): List<String> = buildList {
        add("OrangeLoops")
        add("Sertec")
        add("Whoop")
    }

    fun getProjects(): List<String> = buildList {
        add("[OL-CS] Cloud studio")
        add("[OL-KMPPOC] Kotlin POC")
        add("[OL-Manager] Manager")
        add("[OL-QAS] QA Studio")
        add("[OL-RNS] React Native Studio")
        add("[OL-Templates] Templates")
    }

    fun getTaskTypes(): List<String> = buildList {
        add("Design - Non billable")
        add("Programming - Non billable")
        add("Meeting - Non billable")
    }

    fun deleteTask(taskId: String) {
        tasks = tasks.filter { it.id != taskId }
    }

    fun editTask(taskId: String, title: String, category: String, details: String, hours: String, year: Int, month: Int, day: Int) {
        val task = tasks.find { it.id == taskId }
        val date = LocalDateTime(year, month, day, 0, 0)
        task!!.title = title
        task!!.category = category
        task!!.details = details
        task!!.hours = hours
        task!!.date = date
    }

    fun getTask(id: String): Task? {
        return tasks.find { it.id == id }
    }
}