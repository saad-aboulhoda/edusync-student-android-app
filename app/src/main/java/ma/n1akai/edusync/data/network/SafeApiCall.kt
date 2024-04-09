package ma.n1akai.edusync.data.network

import ma.n1akai.edusync.data.network.responses.AuthResponse
import ma.n1akai.edusync.data.network.responses.ForgetPasswordResponse
import ma.n1akai.edusync.util.UiState
import retrofit2.HttpException

interface SafeApiCall {

    suspend fun <T> safeApiCall(call: suspend () -> T): UiState<T> {
        return try {
            when (val data = call.invoke()) {
                is AuthResponse -> {
                    if (data.error!!) {
                        return UiState.Failure(data.message)
                    }
                    return UiState.Success(data)
                }

                is ForgetPasswordResponse -> {
                    if (data.error!!) {
                        return UiState.Failure(data.message)
                    }
                    return UiState.Success(data)
                }

                else -> return UiState.Success(data)
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException ->
                    UiState.Failure("${throwable.code()}: ${throwable.response()?.errorBody()}")

                else ->
                    UiState.Failure("Network Error: ${throwable.message}")
            }
        }
    }
}