
import Models.Day
import Models.WeekInfo
import Week.getMonthFromName
import Week.getWeek
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn

object WeekViewModel: ViewModel() {
    @NativeCoroutines
    val _selectedWeek = MutableStateFlow(viewModelScope, Week.getCurrentWeek())
    @NativeCoroutinesState
    val selectedWeek: StateFlow<WeekInfo> get() = _selectedWeek
    @NativeCoroutines
    val _selectedDay = MutableStateFlow(viewModelScope, Week.getTodayDate())
    @NativeCoroutinesState
    val selectedDay: StateFlow<Day> get() = _selectedDay

    fun setSelectedDay(day: Day){
        _selectedDay.value = day
    }

    fun setCurrentWeek() {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val startOfWeek = today.minus(today.dayOfWeek.ordinal, DateTimeUnit.DAY)
        _selectedWeek.value = getWeek(startOfWeek)
    }

    fun setCurrentWeek(day: String, monthName: String) {
        val numberDay = day.toInt()
        val month = getMonthFromName(monthName)
        val specificDate = LocalDate(2024, month, numberDay)
        val startOfWeek = specificDate.minus(specificDate.dayOfWeek.ordinal, DateTimeUnit.DAY)
        _selectedWeek.value = getWeek(startOfWeek)
    }

    fun setPreviousWeek(day: String, monthName: String) {
        val numberDay = day.toInt()
        val month = getMonthFromName(monthName)
        val specificDate = LocalDate(2024, month, numberDay)
        val startOfPreviousWeek = specificDate.minus(specificDate.dayOfWeek.ordinal + 7, DateTimeUnit.DAY)
        _selectedWeek.value = getWeek(startOfPreviousWeek)
    }

    fun setFollowingWeek(day: String, monthName: String) {
        val numberDay = day.toInt()
        val month = getMonthFromName(monthName)
        val specificDate = LocalDate(2024, month, numberDay)
        val startOfFollowingWeek = specificDate.plus(7 - specificDate.dayOfWeek.ordinal, DateTimeUnit.DAY)
        _selectedWeek.value = getWeek(startOfFollowingWeek)
    }
}