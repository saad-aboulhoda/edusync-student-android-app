package ma.n1akai.edusync.data.repositories

import ma.n1akai.edusync.data.network.Api
import ma.n1akai.edusync.data.network.SafeApiRequest
import ma.n1akai.edusync.data.network.responses.AuthResponse
import retrofit2.Response

class StudentRepository(
    private val api: Api
) : SafeApiRequest() {

    suspend fun login(email: String, password: String) : AuthResponse {
        return apiRequest {
            api.login(email, password)
        }
    }

}