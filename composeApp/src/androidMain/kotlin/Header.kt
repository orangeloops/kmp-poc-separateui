import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Header(){
    val viewModel = viewModel<WeekViewModel>()
    val selectedDay by viewModel.selectedDay.collectAsState()
    val today: Models.Day = Week.getTodayDate()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .border(1.dp, Color(0xFFD3D3D3), shape = RoundedCornerShape(8.dp))
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = Week.getDateFormatted(selectedDay.day, selectedDay.month, selectedDay.number)
            )
        }
        Row {
            if (!(viewModel.selectedDay.value.day == today.day && viewModel.selectedDay.value.month == today.month)) {
                IconButton(onClick = {
                    viewModel.setCurrentWeek()
                    viewModel.setSelectedDay(Week.getTodayDate())
                }) {
                    Icon(Icons.Default.CalendarToday, contentDescription = "Calendar")
                }
            }
            IconButton(onClick = { viewModel.setPreviousWeek(selectedDay.number, selectedDay.month)
                viewModel.setSelectedDay(Week.getPreviousWeekDate(selectedDay.number, selectedDay.month))
            }) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous")
            }
            IconButton(onClick = { viewModel.setFollowingWeek(selectedDay.number, selectedDay.month)
                viewModel.setSelectedDay(Week.getNextWeekDate(selectedDay.number, selectedDay.month))}) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next")
            }
        }
    }
}