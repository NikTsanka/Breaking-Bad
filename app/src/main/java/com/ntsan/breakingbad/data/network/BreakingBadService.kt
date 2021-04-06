package com.ntsan.breakingbad.data.network

import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadQuotes
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BreakingBadService {

    @GET("/api/characters")
    suspend fun getCharacter(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<BreakingBadCharacters>

    @GET("/user/braking-bad/get-my-characters")
    suspend fun getUserCards(): List<Int>

    @GET("api/characters/{id}")
    suspend fun getCardById(
        @Path("id") id: Int
    ): List<BreakingBadCharacters>

    @GET("/api/characters")
    suspend fun findByName(
        @Query("name") name: String? = null
    ): List<BreakingBadCharacters>

    @GET("/api/quote")
    suspend fun getQuotesByName(
        @Query("author") author: String? = null
    ): List<BreakingBadQuotes>

}