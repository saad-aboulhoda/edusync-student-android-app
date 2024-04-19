package ma.n1akai.edusync.data.models

data class Absent(
    val absent_students_id: Int,
    val student_id: Int,
    val teacher_id: Int,
    val class_id: Int,
    val date: String,
    val start_time: String,
    val end_time: String
) {
    fun getStartTime() = start_time.substring(0, 5)
    fun getEndTime() = end_time.substring(0, 5)
}