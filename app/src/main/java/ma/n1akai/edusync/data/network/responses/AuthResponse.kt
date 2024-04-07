package ma.n1akai.edusync.data.network.responses

import ma.n1akai.edusync.data.models.Student

data class AuthResponse (
    val error: Boolean?,
    val message: String?,
    val token: String?
)