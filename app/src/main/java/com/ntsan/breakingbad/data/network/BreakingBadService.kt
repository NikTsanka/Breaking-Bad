package com.ntsan.breakingbad.data.network

import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BreakingBadService {

    @GET("/api/characters")
    suspend fun getCharacter(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<BreakingBadCharacter>

}