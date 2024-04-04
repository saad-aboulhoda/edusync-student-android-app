package ma.n1akai.edusync.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ma.n1akai.edusync.data.network.Api
import ma.n1akai.edusync.data.network.RetrofitInstance

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideApi() : Api {
        return RetrofitInstance.api
    }

}