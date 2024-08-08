
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun FormField(navController: NavHostController, label: String, route: String) {
    val viewModel = viewModel<FormViewModel>()
    val selectedClient by viewModel.selectedClient.collectAsState()
    val selectedProject by viewModel.selectedProject.collectAsState()
    val selectedTask by viewModel.selectedTask.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(route) }
            .background(color = Color.White),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.Black,
            modifier = Modifier.weight(1f).padding(16.dp)
        )
        Row(
            modifier = Modifier.weight(2f).padding(top = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = if (label == "Client") selectedClient else if (label == "Project") selectedProject else selectedTask,
                color = Color.Black,
                textAlign = TextAlign.End,
                modifier = Modifier
            )
            Icon(
                imageVector = Icons.Outlined.ArrowForwardIos,
                contentDescription = "Arrow Forward",
                tint = Color.LightGray,
                modifier = Modifier.padding(start = 8.dp).size(16.dp)
            )
        }
    }
}
