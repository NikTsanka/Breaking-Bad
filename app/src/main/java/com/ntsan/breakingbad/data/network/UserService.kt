package com.ntsan.breakingbad.data.network

import com.ntsan.breakingbad.data.models.user.UserProfile
import com.ntsan.breakingbad.data.models.user.UserRegistration
import com.ntsan.breakingbad.data.models.user.UserSession
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @POST("/auth/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): UserSession

    @GET("/auth/user")
    suspend fun getUser(): UserProfile

    @POST("/auth/register")
    suspend fun createUser(
        @Header("Content-Type") content_type: String,
        @Body userRegistration: UserRegistration
    )

    @GET("/user/braking-bad/get-my-characters")
    suspend fun getUserCard(): List<Int>

    @POST("/user/braking-bad/save-character")
    suspend fun saveUserCard(@Query("cardId") cardId: Int)

    @DELETE("/user/braking-bad/delete-my-character")
    suspend fun deleteUserCard(@Query("cardId") cardId: Int)
}