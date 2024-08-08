import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.max

@Composable
fun DurationField(){
    val viewModel = viewModel<FormViewModel>()
    val selectedDuration by viewModel.selectedDuration.collectAsState()

    Column {
        Text(text = "Duration", fontSize = 16.sp, color = Color.Black, modifier = Modifier.align(
            Alignment.CenterHorizontally).padding(top = 16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { viewModel.setSelectedDuration(max(0.0, selectedDuration - 60)) }) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease")
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Text(text = formatDuration(selectedDuration), fontSize = 24.sp, color = Color.Gray)
            }
            IconButton(onClick = { viewModel.setSelectedDuration(selectedDuration + 60) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        ) {
            AddDurationAdjustButton("+0:15", 15 * 60)
            AddDurationAdjustButton("+0:30", 30 * 60)
            AddDurationAdjustButton("+1:00", 60 * 60)
            AddDurationAdjustButton("+8:00", 60 * 60 * 8)
        }
    }
}