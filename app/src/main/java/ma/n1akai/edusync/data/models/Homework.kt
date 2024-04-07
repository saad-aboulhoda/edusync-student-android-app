package ma.n1akai.edusync.data.models

data class Homework (
    val homework_id: String,
    val homework: String,
    val course_name: String,
    val created_at: String,
    val finished: Int
)