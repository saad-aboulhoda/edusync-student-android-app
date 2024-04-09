package ma.n1akai.edusync.data.models

data class Attendance(
    val attendance_id: Int,
    val student_id: Int,
    val date: String,
    val is_present: Int,
    val class_id: Int,
    val teacher_id: Int,
    val session_id: Int,
    val month: Int,
    val year: Int,
    val total_present: Int,
    val total_absent: Int,
    val total_leave: Int
)