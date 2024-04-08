package ma.n1akai.edusync.data.network

import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.data.models.Student
import ma.n1akai.edusync.data.models.Test
import retrofit2.Response
import retrofit2.http.GET

interface StudentApi {

    @GET("/student/show")
    suspend fun getStudent(): Response<Student>

    @GET("/student/tests")
    suspend fun getTests(): Response<List<Test>>

    @GET("/student/homeworks")
    suspend fun getHomeworks(): Response<List<Homework>>

}