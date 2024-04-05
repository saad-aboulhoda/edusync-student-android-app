package ma.n1akai.edusync.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

fun String.isValidEmail() : Boolean {
    val emailRegex = Regex("""^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$""")
    return emailRegex.matches(this)
}

fun CoroutineScope.safeLaunch(
    block: suspend CoroutineScope.() -> Unit,
    onError: (String) -> Unit
) {
    this.launch {
        try {
            block()
        } catch (e: ApiException) {
            onError(e.message!!)
        } catch (e: NoInternetException) {
            onError(e.message!!)
        } catch (e: SocketTimeoutException) {
            onError("Connection to server failed or timeout: ${e.message}")
        } catch (e: Exception) {
            onError("Unknown Error has occurred: ${e.message}")
        }
    }
}