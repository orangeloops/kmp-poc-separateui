
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DatePickerField(label: String) {
    val context = LocalContext.current
    val formViewModel: FormViewModel = viewModel()
    val selectedDate = formViewModel.selectedDate.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val calendar = Calendar.getInstance().apply {
        set(selectedDate.value.year, selectedDate.value.monthNumber - 1, selectedDate.value.dayOfMonth)
    }
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            val selectedLocalDate = LocalDate(selectedYear, selectedMonth + 1, selectedDayOfMonth)
            coroutineScope.launch {
                formViewModel.setSelectedDate(selectedLocalDate)
            }
        }, year, month, day
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .clickable { datePickerDialog.show() }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                color = Color.Black,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault()).format(calendar.time),
                color = Color.Black,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(2f)
            )
        }
    }
}
