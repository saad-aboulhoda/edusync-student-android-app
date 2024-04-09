package ma.n1akai.edusync.data.repository

import ma.n1akai.edusync.data.network.AuthApi
import ma.n1akai.edusync.data.network.SafeApiCall
import ma.n1akai.edusync.data.network.responses.AuthResponse
import ma.n1akai.edusync.data.network.responses.ForgetPasswordResponse
import ma.n1akai.edusync.util.UiState

class AuthRepository(
    private val authApi: AuthApi
) : SafeApiCall {

    suspend fun login(email: String, password: String) = safeApiCall {
        authApi.login(email, password)
    }

    suspend fun generateOtp(email: String) = safeApiCall {
        authApi.forgetPassword(email)
    }

    suspend fun verifyOtp(email: String, otp: String) = safeApiCall {
        authApi.verifyOtp(email, otp)
    }

    suspend fun changePassword(email: String, opt: String, password: String) = safeApiCall {
        authApi.changePassword(email, opt, password)
    }

}