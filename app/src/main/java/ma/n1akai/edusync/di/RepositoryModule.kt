package ma.n1akai.edusync.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ma.n1akai.edusync.data.network.Api
import ma.n1akai.edusync.data.repository.AuthRepository

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule  {

    @Provides
    fun provideAuthRepository(api: Api) : AuthRepository {
        return AuthRepository(api)
    }

}