
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object FormViewModel: ViewModel() {
    @NativeCoroutines
    val _selectedClient = MutableStateFlow(viewModelScope, "OrangeLoops")
    @NativeCoroutinesState
    val selectedClient: StateFlow<String> get() = _selectedClient

    @NativeCoroutines
    val _selectedProject = MutableStateFlow(viewModelScope, "[OL-CS] Cloud studio")
    @NativeCoroutinesState
    val selectedProject: StateFlow<String> get() = _selectedProject

    @NativeCoroutines
    val _selectedTask = MutableStateFlow(viewModelScope, "Design - Non billable")
    @NativeCoroutinesState
    val selectedTask: StateFlow<String> get() = _selectedTask

    @NativeCoroutines
    val _selectedDate = MutableStateFlow(viewModelScope, Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
    @NativeCoroutinesState
    val selectedDate: StateFlow<LocalDate> get() = _selectedDate

    @NativeCoroutines
    val _selectedDuration = MutableStateFlow(viewModelScope, 0.0)
    @NativeCoroutinesState
    val selectedDuration: StateFlow<Double> get() = _selectedDuration

    @NativeCoroutines
    val _note = MutableStateFlow(viewModelScope, "")
    @NativeCoroutinesState
    val note: StateFlow<String> get() = _note

    private var refresh: Boolean = true

    fun setSelectedClient(client: String){
        _selectedClient.value = client
        refresh = false
    }

    fun setSelectedProject(project: String){
        _selectedProject.value = project
        refresh = false
    }

    fun setSelectedTask(task: String){
        _selectedTask.value = task
        refresh = false
    }

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun setSelectedDuration(duration: Double){
        _selectedDuration.value = duration
    }

    fun setNote(note: String){
        _note.value = note
    }

    fun String.extractTextBetweenParentheses(): String? {
        val start = this.indexOf('(')
        val end = this.indexOf(')')
        return if (start != -1 && end != -1 && start < end) {
            this.substring(start + 1, end)
        } else {
            null
        }
    }

    fun String.extractTextBeforeParentheses(): String {
        val start = this.indexOf('(')
        return if (start != -1) {
            this.substring(0, start)
        } else {
            this
        }
    }

    fun String.toSeconds(): Double {
        val parts = this.split(":")
        if (parts.size != 2) return 0.0
        val hours = parts[0].toIntOrNull() ?: return 0.0
        val minutes = parts[1].toIntOrNull() ?: return 0.0
        return (hours * 3600 + minutes * 60).toDouble()
    }
    fun setTask(id: String){
        val task = Task.getTask(id)
        if (task != null && refresh){
            setSelectedClient(task.title.extractTextBetweenParentheses()!!)
            setSelectedProject(task.title.extractTextBeforeParentheses()!!)
            setSelectedTask(task.category)
            setSelectedDate(LocalDate(task.date.year, task.date.month, task.date.dayOfMonth))
            setSelectedDuration(task.hours.toSeconds())
            setNote(task.details)
        }
    }

    fun setRefresh(refresh: Boolean){
        this.refresh = refresh
    }
}