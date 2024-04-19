package ma.n1akai.edusync.data.network.responses

data class BaseResponse(
    val error: Boolean,
    val message: String,
    var id: Int = -1
)