package ma.n1akai.edusync.data.models

import java.io.Serializable

data class Student(
    val student_id: Int?,
    val first_name: String?,
    val last_name: String?,
    val phone_number: String?,
    val fathers_name: String?,
    val mothers_name: String?,
    val join_date: String?,
    val email: String?,
    val class_id: String?,
    val class_name: String?,
    val class_year: String?,
    val date_of_birth: String?,
    val avatar: String?
) : Serializable {
    fun getFullName(): String {
        return "${last_name?.uppercase()} ${first_name?.uppercase()}"
    }
}
