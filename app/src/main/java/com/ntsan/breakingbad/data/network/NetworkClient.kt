package com.ntsan.breakingbad.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkClient {

    val breakingBadService by lazy { createBreakingBadService() }

    val userService by lazy { createUserService() }

    private const val USER_AUTHENTICATOR_URL = "https://commschool-android-api.herokuapp.com"
    private const val BREAKING_BAD_API = "https://www.breakingbadapi.com"

    private val loginInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun createUserService(): UserService {
        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.baseUrl(USER_AUTHENTICATOR_URL)
        retrofitBuilder.client(getHttpClient())
        retrofitBuilder.addConverterFactory(moshiConvertor())
        return retrofitBuilder.build().create(UserService::class.java)
    }

    private fun createBreakingBadService(): BreakingBadService {
        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.baseUrl(BREAKING_BAD_API)
        retrofitBuilder.client(
            OkHttpClient().newBuilder()
                .addInterceptor(loginInterceptor)
                .build()
        )
        retrofitBuilder.addConverterFactory(moshiConvertor())
        return retrofitBuilder.build().create(BreakingBadService::class.java)
    }

    private fun getHttpClient() =
        OkHttpClient().newBuilder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(loginInterceptor).build()

    private fun moshiConvertor() =
        MoshiConverterFactory.create(
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        )

}