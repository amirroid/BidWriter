package ir.amirroid.bidwriter.models

import androidx.compose.runtime.Immutable

enum class Role {
    AI, USER
}

@Immutable
data class Message(
    val id: Int,
    val content: String,
    val role: Role,
    val isLoading: Boolean
)
