package ma.n1akai.edusync.data.repository

import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.data.models.Student
import ma.n1akai.edusync.data.models.Test
import ma.n1akai.edusync.data.network.SafeApiCall
import ma.n1akai.edusync.data.network.StudentApi
import ma.n1akai.edusync.util.UiState


class StudentRepository(
    private val api: StudentApi
) : SafeApiCall {


    suspend fun getStudent() = safeApiCall { api.getStudent() }
    suspend fun getTests() = safeApiCall { api.getTests() }
    suspend fun getHomeworks() = safeApiCall { api.getHomeworks() }
    suspend fun getAttendances() = safeApiCall { api.getAttendances() }
}