import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun TaskList(navController: NavHostController){
    val taskModel = viewModel<TaskViewModel>()
    val viewModel = viewModel<WeekViewModel>()
    val selectedDay by viewModel.selectedDay.collectAsState()
    var tasks = taskModel.getTasks(selectedDay.number, selectedDay.month)

    LaunchedEffect(Unit){
        tasks = taskModel.getTasks(selectedDay.number, selectedDay.month)
    }

    tasks.forEach { task ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        navController.navigate("delete_form/${task.id}")
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = task.title )
                    Text(text = task.category, color = Color.Gray)
                    Text(text = task.details, color = Color.Gray)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Play")
                            }
                            Text(
                                text = task.hours,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}