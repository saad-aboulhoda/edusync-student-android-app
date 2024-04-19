package ma.n1akai.edusync.data.network

import ma.n1akai.edusync.data.models.Absent
import ma.n1akai.edusync.data.models.Attendance
import ma.n1akai.edusync.data.models.Fee
import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.data.models.Student
import ma.n1akai.edusync.data.models.Test
import ma.n1akai.edusync.data.models.TestOnline
import ma.n1akai.edusync.data.models.TestQuestion
import ma.n1akai.edusync.data.network.responses.BaseResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StudentApi {

    @GET("/student/show")
    suspend fun getStudent(): Student

    @GET("/student/tests")
    suspend fun getTests(): List<Test>

    @GET("/student/homeworks")
    suspend fun getHomeworks(): List<Homework>

    @FormUrlEncoded
    @POST("/student/homeworks")
    suspend fun checkHomework(
        @Field("homework_id") homeworkId: Int
    ): BaseResponse

    @DELETE("/student/homeworks/{studentHomeworkId}")
    suspend fun uncheckHomework(@Path("studentHomeworkId") studentHomeworkId: Int) : BaseResponse


    suspend
    @GET("/student/absents")
    fun getAbsents(): List<Absent>

    @GET("/student/fees")
    suspend fun getFees(): List<Fee>

    @GET("/student/exams")
    suspend fun getTestsOnline(): List<TestOnline>

    @GET("/student/exams/{id}/questions")
    suspend fun getQuestions(@Path("id") id: Int): List<TestQuestion>

    @FormUrlEncoded
    @POST("/student/exams/{id}")
    suspend fun submitTest(@Path("id") id: Int, @FieldMap map: Map<String, Int>): BaseResponse
}