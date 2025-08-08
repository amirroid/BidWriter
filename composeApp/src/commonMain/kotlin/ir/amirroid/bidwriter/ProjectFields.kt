package ir.amirroid.bidwriter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.amirroid.bidwriter.components.SegmentedButtonPicker

val toneChoices = listOf(
    "Friendly",
    "Formal",
    "Neutral",
)

val languageChoices = listOf(
    "English",
    "Persian"
)

@Composable
fun ProjectFields(
    onConfirm: (String) -> Unit
) {
    var clientName by rememberSaveable { mutableStateOf("") }
    var tone by rememberSaveable { mutableStateOf(toneChoices.first()) }
    var projectDescription by rememberSaveable { mutableStateOf("") }
    var suggestions by rememberSaveable { mutableStateOf("") }
    var portfolioItems by rememberSaveable { mutableStateOf("") }
    var questions by rememberSaveable { mutableStateOf("") }
    var deadline by rememberSaveable { mutableStateOf("") }
    var formattingInstructions by rememberSaveable { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp)
            .systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = clientName,
            onValueChange = { clientName = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Client Name") }
        )
        OutlinedTextField(
            value = projectDescription,
            onValueChange = { projectDescription = it },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp),
            label = { Text("Project Description") }
        )
        OutlinedTextField(
            value = suggestions,
            onValueChange = { suggestions = it },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp),
            label = { Text("Suggestions") }
        )
        OutlinedTextField(
            value = portfolioItems,
            onValueChange = { portfolioItems = it },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 90.dp),
            label = { Text("Portfolio Items") }
        )
        OutlinedTextField(
            value = questions,
            onValueChange = { questions = it },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp),
            label = { Text("Questions") }
        )
        OutlinedTextField(
            value = deadline,
            onValueChange = { deadline = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Deadline") }
        )
        OutlinedTextField(
            value = formattingInstructions,
            onValueChange = { formattingInstructions = it },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 90.dp),
            label = { Text("Formatting Instructions") }
        )
        SegmentedButtonPicker(
            items = toneChoices,
            selectedItem = tone,
            title = "Tone",
            onClick = { tone = it }
        ) { Text(it) }
        Button(
            onClick = {
                onConfirm.invoke(
                    buildString {
                        appendLine("1. Client's Name: $clientName")
                        appendLine("2. Tone: $tone")
                        appendLine("3. Project Description: $projectDescription")
                        appendLine("4. My Suggestions: $suggestions")
                        appendLine("5. Portfolio Items: $portfolioItems")
                        appendLine("6. My Questions: $questions")
                        appendLine("7. Deadline: $deadline")
                        appendLine("8. Formatting Instructions: ${formattingInstructions.ifBlank { "(empty)" }}")
                    }
                )

            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Send")
        }
    }
}