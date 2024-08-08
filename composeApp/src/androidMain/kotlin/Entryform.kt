
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

fun formatDuration(duration: Double): String {
    val hours = duration.toInt() / 3600
    val minutes = duration.toInt() % 3600 / 60
    return String.format("%02d:%02d", hours, minutes)
}
@Composable
fun EntryForm(navController: NavHostController) {
    val viewModel = viewModel<FormViewModel>()
    val selectedDuration by viewModel.selectedDuration.collectAsState()
    val note by viewModel.note.collectAsState()
    val taskViewModel = viewModel<TaskViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFAF0))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "New time entry", fontSize = 16.sp, modifier = Modifier.weight(6f), textAlign = TextAlign.Center)
            IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.weight(1f)) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            FormField(navController, label = "Client", route = "client_list_view")
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
            FormField(navController, label = "Project", route = "project_list_view")
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
            FormField(navController, label = "Task", route = "task_list_view")
        }

        Spacer(modifier = Modifier.height(8.dp))

        DatePickerField(label = "Date")

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            DurationField()
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Leave blank to start a timer",
            color = Color.LightGray,
            fontSize = 16.sp // Ajusta el tamaño de la fuente según lo necesites
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = note,
            onValueChange = { viewModel.setNote(it) },
            placeholder = { Text("Write a note (optional)") },
            modifier = Modifier.fillMaxWidth().background(Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                backgroundColor = Color.White,
                cursorColor = Color(0xFF007D00),
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                taskViewModel.addTask("${viewModel.selectedProject.value} (${viewModel.selectedClient.value})", viewModel.selectedTask.value, viewModel.note.value, formatDuration(selectedDuration), viewModel.selectedDate.value.year, viewModel.selectedDate.value.monthNumber, viewModel.selectedDate.value.dayOfMonth)
                navController.navigate(Screen.Time.route)
            },
            modifier = Modifier.fillMaxWidth().heightIn(min = 40.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF008000)),
            elevation = ButtonDefaults.elevation(0.dp)
        ) {
            if (selectedDuration == 0.0) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White
                )
                Text("Start timer", color = Color.White)
            } else {
                Text("Save entry", color = Color.White)
            }
        }
    }
}
