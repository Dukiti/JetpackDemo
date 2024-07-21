package com.dungnm.example.compose.di

import com.dungnm.example.compose.network.service.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    @Provides
//    @Singleton
//    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
//        return OkHttpClient.Builder().apply {
//            val interceptor = HttpLoggingInterceptor()
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//            addInterceptor(interceptor)
//            readTimeout(60, TimeUnit.SECONDS)
//            connectTimeout(60, TimeUnit.SECONDS)
//            writeTimeout(60, TimeUnit.SECONDS)
//        }.build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder().client(okHttpClient).baseUrl(DomainProperties.API_URL)
//            .addConverterFactory(GsonConverterFactory.create()).build()
//    }

    @Provides
    @Singleton
    fun provideGithubComposeService(retrofit: Retrofit): GithubService {
        return retrofit.create(GithubService::class.java)
    }
}