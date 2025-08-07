package com.example.sportynews.di

import com.example.sportynews.data.ApiService
import com.example.sportynews.data.repositories.NewsRepository
import com.example.sportynews.data.repositories.NewsRepositoryImpl
import com.example.sportynews.domain.useCase.GetNewsHeadlinesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Accept", "application/json")
                    .header("Upgrade", "TLS/1.2") // required header
                    // Add App-Version header if needed
                    .header("App-Version", "1.0.0")
                    .build()
                chain.proceed(request)
            }
            .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/") // ✅ ensure HTTPS
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideRepository(api: ApiService): NewsRepository =
        NewsRepositoryImpl(api)

    @Provides
    fun provideUseCase(repo: NewsRepository): GetNewsHeadlinesUseCase =
        GetNewsHeadlinesUseCase(repo)

}