package ma.n1akai.edusync.data.network

import ma.n1akai.edusync.data.models.Attendance
import ma.n1akai.edusync.data.models.Fee
import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.data.models.Student
import ma.n1akai.edusync.data.models.Test
import ma.n1akai.edusync.data.models.TestOnline
import ma.n1akai.edusync.data.models.TestQuestion
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentApi {

    @GET("/student/show")
    suspend fun getStudent(): Student

    @GET("/student/tests")
    suspend fun getTests(): List<Test>

    @GET("/student/homeworks")
    suspend fun getHomeworks(): List<Homework>

    @GET("/student/attendances")
    suspend fun getAttendances(): List<Attendance>

    @GET("/student/fees")
    suspend fun getFees(): List<Fee>

    @GET("/student/exams")
    suspend fun getTestsOnline(): List<TestOnline>

    @GET("/student/exams/{id}/questions")
    suspend fun getQuestions(@Path("id") id: Int) : List<TestQuestion>

}