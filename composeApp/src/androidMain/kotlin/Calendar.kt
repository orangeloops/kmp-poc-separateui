import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Calendar(){
    val viewModel : WeekViewModel = viewModel()
    val state by viewModel.selectedWeek.collectAsState()
    val selectedDay by viewModel.selectedDay.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setCurrentWeek(selectedDay.number, selectedDay.month)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        state.week.forEach { dayInfo ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = dayInfo.day.take(3))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                ) {
                    if (dayInfo.number == selectedDay.number && dayInfo.month == selectedDay.month){
                        Canvas(
                            modifier = Modifier.matchParentSize()
                        ) {
                            drawCircle(
                                color = if (dayInfo.number == Week.getTodayDate().number && dayInfo.month == Week.getTodayDate().month) Color(0xFFFFA500) else Color(0xFF000000),
                                radius = size.minDimension / 2
                            )
                        }
                    }
                    ClickableText(
                        text = AnnotatedString(
                            text = dayInfo.number,
                            spanStyles = listOf(
                                AnnotatedString.Range(
                                    item = SpanStyle(color =
                                    if (dayInfo.number == selectedDay.number && dayInfo.month == selectedDay.month){
                                        Color.White
                                    } else {
                                        if (dayInfo.number == Week.getTodayDate().number && dayInfo.month == Week.getTodayDate().month) Color(0xFFFFA500) else Color(0xFF000000)
                                    }),
                                    start = 0,
                                    end = dayInfo.number.length
                                )
                            )
                        ),                                onClick = {
                            viewModel.setSelectedDay(dayInfo)
                        },
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
                Text(
                    text = dayInfo.hours,
                    color = if (dayInfo.number == Week.getTodayDate().number && dayInfo.month == Week.getTodayDate().month) Color(0xFFFFA500) else Color.Black
                )
            }
        }
    }
}