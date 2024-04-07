package ma.n1akai.edusync.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ma.n1akai.edusync.data.network.AuthApi
import ma.n1akai.edusync.data.network.interceptor.NetworkConnectionInterceptor
import ma.n1akai.edusync.data.network.RetrofitInstance
import ma.n1akai.edusync.data.network.StudentApi
import ma.n1akai.edusync.data.network.interceptor.AuthInterceptor
import ma.n1akai.edusync.util.TokenManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideTokenManager(sharedPreferences: SharedPreferences): TokenManager {
        return TokenManager(sharedPreferences)
    }

    @Provides
    fun provideNetworkConnectionInterceptor(@ApplicationContext context: Context):
            NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(context)
    }

    @Provides
    fun provideAuthInterceptor(
        @ApplicationContext context: Context,
        tokenManager: TokenManager
    ): AuthInterceptor {
        return AuthInterceptor(context, tokenManager)
    }

    @Provides
    fun provideAuthApi(
        interceptor: NetworkConnectionInterceptor,
        authInterceptor: AuthInterceptor
    ): AuthApi {
        return RetrofitInstance(interceptor, authInterceptor).buildApi(AuthApi::class.java)
    }

    @Provides
    fun provideStudentApi(
        interceptor: NetworkConnectionInterceptor,
        authInterceptor: AuthInterceptor
    ): StudentApi {
        return RetrofitInstance(interceptor, authInterceptor).buildApi(StudentApi::class.java)
    }


}