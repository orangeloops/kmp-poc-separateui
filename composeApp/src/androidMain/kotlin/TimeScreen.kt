
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun TimeScreen(navController: NavHostController) {
    val viewModel = viewModel<WeekViewModel>()
    val formViewModel = viewModel<FormViewModel>()
    val state by viewModel.selectedWeek.collectAsState()
    viewModel<TaskViewModel>()

    LaunchedEffect(Unit) {
        formViewModel.setNote("")
        formViewModel.setSelectedDuration(0.0)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Time", fontSize = 32.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Header()

            Calendar()
        }

        Divider(color = Color(0xFFD3D3D3), thickness = 1.dp)
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFFFFFAF0))
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Week total: ${state.hours}",
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable(onClick = { /* TODO */ })
                    ) {
                        Text(
                            text = "Submit",
                            color = Color.Blue,
                            style = TextStyle(
                                fontSize = 16.sp
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                TaskList(navController = navController)

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { navController.navigate(Screen.EntryForm.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF007D00)),
                    elevation = ButtonDefaults.elevation(0.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.White,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text(text = "Track time", color = Color.White)
                    }
                }
            }
        }
    }
}