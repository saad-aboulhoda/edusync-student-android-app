package ma.n1akai.edusync.data.repository

import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.data.models.Student
import ma.n1akai.edusync.data.models.Test
import ma.n1akai.edusync.data.network.SafeApiRequest
import ma.n1akai.edusync.data.network.StudentApi
import ma.n1akai.edusync.util.UiState


class StudentRepository(
    private val api: StudentApi
) : SafeApiRequest() {


    suspend fun getStudent(result: (UiState<Student>) -> Unit) {
        val student = apiRequest {
            api.getStudent()
        }

        result.invoke(UiState.Success(student))
    }

    suspend fun getTests(): UiState<List<Test>> {
        val tests = apiRequest { api.getTests() }
        return UiState.Success(tests)
    }

    suspend fun getHomeworks(): UiState<List<Homework>> {
        val homeworks = apiRequest { api.getHomeworks() }
        return UiState.Success(homeworks)
    }

}