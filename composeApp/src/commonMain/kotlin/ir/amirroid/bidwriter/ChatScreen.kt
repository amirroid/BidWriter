package ir.amirroid.bidwriter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.CopyAll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mikepenz.markdown.m3.Markdown
import ir.amirroid.bidwriter.models.Message
import ir.amirroid.bidwriter.models.Role

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel()
) {
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val messageText = viewModel.messageText

    val scrollState = rememberScrollState()

    LaunchedEffect(messages) {
        if (scrollState.isScrollInProgress || scrollState.value < scrollState.maxValue - 100) return@LaunchedEffect
        scrollState.scrollTo(scrollState.maxValue)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(title = { Text("Messages") }, navigationIcon = {
            IconButton(onClick = viewModel::clearMessages) {
                Icon(Icons.Rounded.ArrowBackIosNew, contentDescription = null)
            }
        })
        Column(
            modifier = Modifier.weight(1f).fillMaxWidth().verticalScroll(scrollState)
        ) {
            messages.forEachIndexed { index, message ->
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
            keyboardActions = KeyboardActions(
                onSend = {
                    viewModel.sendMessage(messageText)
                    viewModel.messageText = ""
                }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.sendMessage(messageText)
                    viewModel.messageText = ""
                }) {
                    Icon(Icons.AutoMirrored.Rounded.Send, contentDescription = null)
                }
            }
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
    val clipboard = LocalClipboardManager.current

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
                    Markdown(message.content, modifier = Modifier.fillMaxWidth())
                }
                IconButton(onClick = { clipboard.setText(AnnotatedString(message.content)) }) {
                    Icon(Icons.Rounded.CopyAll, contentDescription = null)
                }
            }
        }
    }
}