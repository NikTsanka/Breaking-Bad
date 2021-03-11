package com.ntsan.breakingbad.data.network

import com.ntsan.breakingbad.data.models.UserProfile
import com.ntsan.breakingbad.data.models.UserSession
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @POST("/auth/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<UserSession>

    @GET("/auth/user")
    suspend fun getUser(): Response<UserProfile>
}