
import Models.Day
import Models.WeekInfo
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn

object Week {
    fun getDateFormatted(day: String, m: String, dayOfMonth: String): String {
        val currentDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

        val month = currentDate.month.name.lowercase()

        return if (currentDate.dayOfMonth.toString() == dayOfMonth && month == m){
            "Today: ${day.lowercase().replaceFirstChar { it.uppercase() }}, ${m.lowercase().replaceFirstChar { it.uppercase() }.substring(0,3)} $dayOfMonth"
        } else {
            "${day.lowercase().replaceFirstChar { it.uppercase() }}, ${m.lowercase().replaceFirstChar { it.uppercase() }.substring(0,3)} $dayOfMonth"
        }
    }

    fun getTodayDate(): Day {
        val currentDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

        val dayOfWeek = currentDate.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
        val month = currentDate.month.name.lowercase()

        return Day(day = dayOfWeek, number = currentDate.dayOfMonth.toString(), hours= "0:00", month= month)
    }

    fun getWeek(startDate: LocalDate): WeekInfo {
        var generalHours = "0:00"
        val list =  List(7) { i ->
            val date = startDate.plus(i, DateTimeUnit.DAY)
            var hours = "0:00"
            for (t: Models.Task in Task.getTasks()){
                if (t.date.year == date.year && t.date.month.number == date.month.number && t.date.dayOfYear == date.dayOfYear){
                    hours = Task.addHours(hours, t.hours)
                    generalHours = Task.addHours(generalHours, t.hours)
                }
            }
            Day(
                day = date.dayOfWeek.name,
                number = date.dayOfMonth.toString(),
                hours = hours,
                month = date.month.name.toLowerCase()
            )
        }
        return WeekInfo(list, generalHours)
    }

    fun getMonthFromName(monthName: String): Month {
        return Month.valueOf(monthName.uppercase())
    }
    fun getNextWeekDate(day: String, monthName: String): Day {
        val numberDay = day.toInt()
        val month = getMonthFromName(monthName)
        val date = LocalDate(2024, month, numberDay)
        val nextWeekDate = date.plus(7, DateTimeUnit.DAY)
        var hours = "0:00"
        for (t: Models.Task in Task.getTasks()){
            if (t.date == date){
                hours = Task.addHours(hours, t.hours)
            }
        }
        return Day(day = nextWeekDate.dayOfWeek.name, number = nextWeekDate.dayOfMonth.toString(), hours = hours, month = nextWeekDate.month.name.lowercase())
    }

    fun getPreviousWeekDate(day: String, monthName: String): Day {
        val numberDay = day.toInt()
        val month = getMonthFromName(monthName)
        val date = LocalDate(2024, month, numberDay)
        val nextWeekDate = date.minus(7, DateTimeUnit.DAY)
        var hours = "0:00"
        for (t: Models.Task in Task.getTasks()){
            if (t.date == date){
                hours = Task.addHours(hours, t.hours)
            }
        }
        return Day(day = nextWeekDate.dayOfWeek.name, number = nextWeekDate.dayOfMonth.toString(), hours = hours, month = nextWeekDate.month.name.lowercase())
    }

    fun getCurrentWeek(): WeekInfo {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val startOfWeek = today.minus(today.dayOfWeek.ordinal, DateTimeUnit.DAY)
        return getWeek(startOfWeek)
    }

}