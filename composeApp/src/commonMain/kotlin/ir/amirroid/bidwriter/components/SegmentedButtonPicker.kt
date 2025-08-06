package ir.amirroid.bidwriter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> SegmentedButtonPicker(
    title: String,
    items: List<T>,
    selectedItem: T,
    onClick: (T) -> Unit,
    content: @Composable (T) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(title, style = MaterialTheme.typography.bodySmall)
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, item ->
                SegmentedButton(
                    selected = item == selectedItem,
                    shape = SegmentedButtonDefaults.itemShape(index, items.size),
                    onClick = { onClick.invoke(item) }
                ) {
                    content.invoke(item)
                }
            }
        }
    }
}