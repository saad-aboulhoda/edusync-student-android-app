package ma.n1akai.edusync.data.models

data class TestQuestion(
    val question_id: Int,
    val question: String,
    val mark: String,
    val answers: List<TestOnlineAnswer>
)