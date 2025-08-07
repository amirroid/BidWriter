package ir.amirroid.bidwriter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ir.amirroid.bidwriter.models.Message
import ir.amirroid.bidwriter.models.Role

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel()
) {
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val messageText = viewModel.messageText

    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(title = { Text("Messages") })
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            itemsIndexed(messages, key = { _, item -> item.id }) { index, message ->
                MessageItem(message)
                if (index != messages.size) {
                    HorizontalDivider()
                }
            }
        }
        OutlinedTextField(
            value = messageText,
            onValueChange = { viewModel.messageText = it },
            modifier = Modifier.fillMaxWidth().navigationBarsPadding().padding(12.dp),
            placeholder = { Text("Message...") },
            keyboardActions = KeyboardActions {
                viewModel.sendMessage(messageText)
                viewModel.messageText = ""
            },
            singleLine = true
        )
    }


    if (messages.isEmpty()) {
        ProjectFields(
            onConfirm = viewModel::sendDefaultPrompt
        )
    }
}

@Composable
fun MessageItem(message: Message) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(if (message.role == Role.AI) MaterialTheme.colorScheme.surface else Color.Transparent)
            .padding(12.dp)
    ) {
        if (message.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                strokeCap = StrokeCap.Round
            )
        } else {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                SelectionContainer {
                    Text(message.content, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}