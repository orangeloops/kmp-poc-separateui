import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

fun getDateFormatted(day: String, m: String, dayOfMonth: String): String {
    val currentDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

    val dayOfWeek = currentDate.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
    val month = currentDate.month.name.lowercase().replaceFirstChar { it.uppercase() }

    return if (currentDate.dayOfMonth.toString() == dayOfMonth && month == m){
        //today
        "Today: $day, $m $dayOfMonth"
    } else {
        "$dayOfWeek, $month ${currentDate.dayOfMonth}"
    }

}