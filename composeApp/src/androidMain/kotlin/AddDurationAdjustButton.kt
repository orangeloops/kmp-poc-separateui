
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddDurationAdjustButton(label: String, adjustment: Int){
    val viewModel = viewModel<FormViewModel>()
    val selectedDuration by viewModel.selectedDuration.collectAsState()
    OutlinedButton(
        onClick = { viewModel.setSelectedDuration(selectedDuration + adjustment) },
        border = BorderStroke(1.dp, Color.LightGray),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(label, color = Color(0xFF008000), modifier = Modifier.padding(horizontal = 8.dp)) // Adjusted padding for the text
    }
}