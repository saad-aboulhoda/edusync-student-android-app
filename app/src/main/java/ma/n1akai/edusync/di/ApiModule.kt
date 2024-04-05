package ma.n1akai.edusync.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ma.n1akai.edusync.data.network.Api
import ma.n1akai.edusync.data.network.NetworkConnectionInterceptor
import ma.n1akai.edusync.data.network.RetrofitInstance

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideInterceptor(@ApplicationContext context: Context): NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(context)
    }

    @Provides
    fun provideApi(interceptor: NetworkConnectionInterceptor) : Api {
        return RetrofitInstance(interceptor).api
    }



}