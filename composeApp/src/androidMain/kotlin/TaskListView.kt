
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.sharp.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun TaskListView(navController: NavHostController) {
    val viewModel = viewModel<FormViewModel>()
    val selectedTask by viewModel.selectedTask.collectAsState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val tasks = listOf(
        "Internal - Non billable",
        "Meeting - Non billable",
        "Programming - Non billable",
        "Testing - Non billable",
        "Training - Non billable"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFFAF0))
                .padding(8.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBackIosNew,
                    contentDescription = "Arrow Back",
                    tint = Color.DarkGray,
                    modifier = Modifier.padding(start = 8.dp).size(16.dp).clickable { navController.popBackStack() }
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "[OL-KMPPOC] Kotlin POC",
                        color = Color(0xFFFF4C00),
                        fontSize = 12.sp,
                        lineHeight = 12.sp
                    )
                    Text(
                        text = "Choose a task",
                        color = Color.Black,
                        fontSize = 16.sp,
                        lineHeight = 16.sp
                    )
                }
                Text(
                    text = "Done",
                    color = Color.Blue,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFFFF4C00).copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.weight(1f),
                        decorationBox = { innerTextField ->
                            if (searchQuery.text.isEmpty()) {
                                Text(
                                    text = "Search by task name",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                }
                if (searchQuery.text.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Cancel",
                        color = Color.Blue,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable {
                            searchQuery = TextFieldValue("")
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        ){
            Text(
                text = "Non-billable",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Column {
                tasks.filter {
                    it.contains(searchQuery.text, ignoreCase = true)
                }.forEach { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.setSelectedTask(task) }
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Sharp.Star,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = task,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )
                        if (selectedTask == task) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF007D00),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
