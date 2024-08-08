import kotlinx.datetime.LocalDateTime
import kotlin.random.Random

class Models {

    data class Task(
        val id: String = Random.nextBytes(16).joinToString("") { it.toString(16) },
        var title: String,
        var category: String,
        var details: String,
        var hours: String,
        var date: LocalDateTime,
        var inProgress: Boolean
    )
    data class Day (
        val id: String = Random.nextBytes(16).joinToString("") { it.toString(16) },
        val day: String,
        val number: String,
        var hours: String,
        val month: String
    )

    data class WeekInfo (
        val week: List<Day>,
        var hours: String
    )
}