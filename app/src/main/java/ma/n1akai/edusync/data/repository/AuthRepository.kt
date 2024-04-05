package ma.n1akai.edusync.data.repository

import ma.n1akai.edusync.data.network.Api
import ma.n1akai.edusync.data.network.SafeApiRequest
import ma.n1akai.edusync.data.network.responses.AuthResponse
import ma.n1akai.edusync.data.network.responses.ForgetPasswordResponse
import ma.n1akai.edusync.util.UiState

class AuthRepository(
    private val api: Api
) : SafeApiRequest() {

    suspend fun login(email: String, password: String, result: (UiState<String>) -> Unit) {
        val response: AuthResponse = apiRequest {
            api.login(email, password)
        }
        if (!response.error!!) {
            result.invoke(UiState.Success(response.message!!))
        } else {
            result.invoke(UiState.Failure(response.message!!))
        }
    }

    suspend fun generateOtp(email: String, result: (UiState<String>) -> Unit) {
        val response: ForgetPasswordResponse = apiRequest {
            api.forgetPassword(email)
        }
        if (!response.error) {
            result.invoke(UiState.Success(response.message))
        } else {
            result.invoke(UiState.Failure(response.message))
        }
    }

    suspend fun verifyOtp(email: String, otp: String, result: (UiState<String>) -> Unit) {
        val response: ForgetPasswordResponse = apiRequest {
            api.verifyOtp(email, otp)
        }
        if (!response.error) {
            result.invoke(UiState.Success(response.message))
        } else {
            result.invoke(UiState.Failure(response.message))
        }
    }

    suspend fun changePassword(email: String, opt: String, password: String, result: (UiState<String>) -> Unit) {
        val response: ForgetPasswordResponse = apiRequest {
            api.changePassword(email, opt, password)
        }
        if (!response.error) {
            result.invoke(UiState.Success(response.message))
        } else {
            result.invoke(UiState.Failure(response.message))
        }
    }

}