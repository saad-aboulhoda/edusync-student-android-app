package ma.n1akai.edusync.data.network

import ma.n1akai.edusync.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>) : T {
        val res = call.invoke()
        if (res.isSuccessful) {
            return res.body()!!
        } else {
            val error = res.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) { }
                message.append("\n")
            }
            message.append("Error code: ${res.code()}")
            throw ApiException(message.toString())
        }
    }

}