package ma.n1akai.edusync.data.network

import ma.n1akai.edusync.data.network.responses.AuthResponse
import ma.n1akai.edusync.data.network.responses.ForgetPasswordResponse
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("auth/students")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("forget-password/student")
    suspend fun forgetPassword(
        @Field("email") email: String
    ) : Response<ForgetPasswordResponse>

    @FormUrlEncoded
    @POST("/very-otp/student")
    suspend fun verifyOtp(
        @Field("email") email: String,
        @Field("otp") otp: String
    ) : Response<ForgetPasswordResponse>

    @FormUrlEncoded
    @POST("/change-password/student")
    suspend fun changePassword(
        @Field("email") email: String,
        @Field("otp") otp: String,
        @Field("password") password: String
    ) : Response<ForgetPasswordResponse>

}