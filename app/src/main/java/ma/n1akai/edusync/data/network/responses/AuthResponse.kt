package ma.n1akai.edusync.data.network.responses

import ma.n1akai.edusync.data.models.User

data class AuthResponse (
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User?
)