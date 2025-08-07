package ir.amirroid.bidwriter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.chat.TextContent
import com.aallam.openai.api.logging.Logger
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIHost
import ir.amirroid.bidwriter.models.Message
import ir.amirroid.bidwriter.models.Role
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow(emptyList<Message>())
    val messages = _messages.asStateFlow()

    var messageText by mutableStateOf("")

    private val openAI = OpenAI(
        token = BuildKonfig.OPENAI_API_KEY,
        logging = LoggingConfig(logger = Logger.Default),
        host = OpenAIHost(BuildKonfig.HOST)
    )
    private val model = ModelId("gpt-4o")


    fun clearMessages() = _messages.update { emptyList() }

    private fun addMessage(
        role: Role,
        content: String = "",
        id: Int? = null,
        isLoading: Boolean = false
    ): Int {
        var messageId = id ?: 0
        _messages.update { list ->
            if (id == null) {
                messageId = (list.maxOfOrNull { it.id } ?: 0) + 1
                list + Message(
                    content = content,
                    role = role,
                    id = messageId,
                    isLoading = isLoading
                )
            } else {
                val index = list.indexOfFirst { it.id == id }
                if (index == -1) {
                    messageId = id
                    list + Message(
                        content = content,
                        role = role,
                        id = id,
                        isLoading = isLoading
                    )
                } else {
                    messageId = id
                    list.toMutableList().also {
                        it[index] = Message(
                            content = content,
                            role = role,
                            id = id,
                            isLoading = isLoading
                        )
                    }
                }
            }
        }
        return messageId
    }

    fun sendMessage(text: String) = viewModelScope.launch(Dispatchers.IO) {
        addMessage(Role.USER, text)
        sendMessageRequest(
            ChatCompletionRequest(
                model = model,
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.System,
                        messageContent = TextContent(prompt)
                    ),
                    *_messages.value.map {
                        ChatMessage(
                            role = if (it.role == Role.AI) ChatRole.System else ChatRole.User,
                            messageContent = TextContent(it.content)
                        )
                    }.toTypedArray(),
                    ChatMessage(
                        role = ChatRole.User,
                        messageContent = TextContent(text)
                    ),
                )
            )
        )
    }

    fun sendDefaultPrompt(parameters: String) = viewModelScope.launch(Dispatchers.IO) {
        addMessage(Role.USER, parameters)
        sendMessageRequest(
            ChatCompletionRequest(
                model = model,
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.System,
                        messageContent = TextContent(prompt)
                    ),
                    ChatMessage(
                        ChatRole.User,
                        messageContent = TextContent(parameters)
                    ),
                )
            )
        )
    }


    private suspend fun sendMessageRequest(request: ChatCompletionRequest) {
        val aiResponseId = addMessage(role = Role.AI, isLoading = true)
        val fullResponse = StringBuilder()
        openAI.chatCompletions(request).collectLatest { chunk ->
            println(chunk.choices)
            val content = chunk.choices.firstOrNull()?.delta?.content
            if (!content.isNullOrEmpty()) {
                fullResponse.append(content)
                addMessage(Role.AI, fullResponse.toString(), id = aiResponseId)
            }
        }
    }
}