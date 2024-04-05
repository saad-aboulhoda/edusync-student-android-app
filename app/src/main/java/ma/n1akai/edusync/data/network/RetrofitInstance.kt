package ma.n1akai.edusync.data.network

import com.chaos.view.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitInstance(
    private val interceptor: NetworkConnectionInterceptor
) {

    private val retrofit: Retrofit by lazy {
        val client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .also {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                it.addInterceptor(logging)
            }
            .build()


        Retrofit.Builder()
            .baseUrl("https://edusyncapi.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api: Api by lazy {
        retrofit.create(Api::class.java)
    }
}