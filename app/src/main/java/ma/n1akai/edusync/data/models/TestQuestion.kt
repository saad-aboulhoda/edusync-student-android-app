package ma.n1akai.edusync.data.models

data class TestQuestion(
    val question: String,
    val mark: String,
    val answers: List<String>
)