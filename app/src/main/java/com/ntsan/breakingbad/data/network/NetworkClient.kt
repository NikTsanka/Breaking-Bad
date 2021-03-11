package com.ntsan.breakingbad.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkClient {

    val userService by lazy { createUserService() }

    private fun createUserService(): UserService {
        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.baseUrl("https://commschool-android-api.herokuapp.com")
        retrofitBuilder.client(getHttpClient())
        retrofitBuilder.addConverterFactory(moshiConvertor())
        return retrofitBuilder.build().create(UserService::class.java)
    }

    private fun getHttpClient() =
        OkHttpClient().newBuilder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()

    private fun moshiConvertor() =
        MoshiConverterFactory.create(
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        )
}