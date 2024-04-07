package ma.n1akai.edusync.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ma.n1akai.edusync.data.network.AuthApi
import ma.n1akai.edusync.data.network.StudentApi
import ma.n1akai.edusync.data.repository.AuthRepository
import ma.n1akai.edusync.data.repository.StudentRepository

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule  {

    @Provides
    fun provideAuthRepository(authApi: AuthApi) : AuthRepository {
        return AuthRepository(authApi)
    }

    @Provides
    fun provideStudentRepository(studentApi: StudentApi) : StudentRepository {
        return StudentRepository(studentApi)
    }
}