package com.ntsan.breakingbad.data.network

import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.data.models.breakingbad.Data
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BreakingBadService {

    @GET("/api/characters")
    suspend fun getCharacter(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<BreakingBadCharacters>

    @GET("api/characters/{id}")
    suspend fun getCardById(
        @Path("id") id: Int
    ): Data<BreakingBadCharacters>


    @GET("/api/characters")
    suspend fun findByName(
        @Query("name") name: String? = null
    ): List<BreakingBadCharacters>

}