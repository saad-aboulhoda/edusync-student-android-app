package ma.n1akai.edusync.data.models

import java.io.Serializable

data class TestOnline (
    val test_online_id: Int,
    val test_online_name: String,
    val class_id: Int,
    val duration: String,
    val score: Double,
    val course_name: String
) : Serializable