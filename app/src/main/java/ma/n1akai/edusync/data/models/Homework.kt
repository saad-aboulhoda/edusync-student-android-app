package ma.n1akai.edusync.data.models

data class Homework(
    var student_homework: Int,
    val homework_id: Int,
    val homework: String,
    val course_name: String,
    val created_at: String,
    var finished: Int,
    val description: String
)