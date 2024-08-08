
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import kotlinx.coroutines.flow.StateFlow

class TaskViewModel : ViewModel() {
    @NativeCoroutines
    val _tasks = MutableStateFlow<List<Models.Task>>(viewModelScope, emptyList())
    @NativeCoroutinesState
    val tasks: StateFlow<List<Models.Task>> get() = _tasks

    fun getTasks(number: String, month: String): List<Models.Task> {
        var filtered = mutableListOf<Models.Task>()
        for (task : Models.Task in Task.getTasks()){
            if (task.date.dayOfMonth.toString() == number && task.date.month.name.lowercase() == month.lowercase()){
                filtered.add(task)
            }
        }
        return filtered
    }

    init {
        loadTasks()
    }
    private fun loadTasks() {
        _tasks.value = Task.getTasks()
    }

    fun addTask(title: String, category: String, details: String, hours: String, year: Int, month: Int, day: Int) {
        Task.addTask(title, category, details, hours, year, month, day)
        loadTasks()
    }

    fun addPendingTask(title: String, category: String, details: String) {
        Task.addPendingTask(title, category, details)
        loadTasks()
    }

    fun finishPendingTask(taskId: String) {
        Task.finishPendingTask(taskId)
        loadTasks()
    }

    fun deleteTask(taskId: String) {
        Task.deleteTask(taskId)
        loadTasks()
        FormViewModel.setRefresh(true);
    }

    fun editTask(taskId: String, title: String, category: String, details: String, hours: String, year: Int, month: Int, day: Int) {
        Task.editTask(taskId, title, category, details, hours, year, month, day)
        loadTasks()
        FormViewModel.setRefresh(true);
    }
}
