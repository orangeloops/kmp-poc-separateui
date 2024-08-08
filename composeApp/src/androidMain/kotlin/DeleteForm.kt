
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun DeleteFormScreen(navController: NavHostController, taskId: String?) {
    val viewModel = viewModel<FormViewModel>()
    val selectedDuration by viewModel.selectedDuration.collectAsState()
    val note by viewModel.note.collectAsState()
    val taskViewModel = viewModel<TaskViewModel>()
    val showDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (taskId != null) {
            viewModel.setTask(taskId)
        }
    }

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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Cancel",
                color = Color.Blue,
                fontSize = 16.sp,
                modifier = Modifier
                    .clickable {
                        viewModel.setRefresh(true)
                        navController.popBackStack()
                    }
                    .padding(start = 8.dp)
            )

            Text(
                text = "Edit time entry",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Delete",
                color = Color(0xFFCC0000),
                fontSize = 16.sp,
                modifier = Modifier
                    .clickable {
//                        taskViewModel.deleteTask(taskId!!)
//                        navController.popBackStack()
                        showDialog.value = true
                    }
                    .padding(end = 8.dp)
            )
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
            DeleteDurationField()
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = note,
            onValueChange = { viewModel.setNote(it) },
            placeholder = { Text("Write a note (optional)") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
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
                taskViewModel.editTask(taskId!!, "${viewModel.selectedProject.value} (${viewModel.selectedClient.value})", viewModel.selectedTask.value, viewModel.note.value, formatDuration(selectedDuration), viewModel.selectedDate.value.year, viewModel.selectedDate.value.monthNumber, viewModel.selectedDate.value.dayOfMonth)
                navController.navigate(Screen.Time.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 40.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF008000)),
            elevation = ButtonDefaults.elevation(0.dp)
        ) {
            Text("Update entry", color = Color.White)
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Delete Time Entry") },
            text = { Text("Delete this Time entry permanently?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        taskViewModel.deleteTask(taskId!!)
                        showDialog.value = false
                        navController.popBackStack()
                    }
                ) {
                    Text(text ="OK",
                        color = Color(0xFF007D80))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog.value = false }
                ) {
                    Text(text ="CANCEL",
                        color = Color(0xFF007D80))
                }
            }
        )
    }
}
