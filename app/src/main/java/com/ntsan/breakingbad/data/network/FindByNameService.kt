package com.ntsan.breakingbad.data.network

import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import retrofit2.http.GET
import retrofit2.http.Query

interface FindByNameService {

    @GET("/api/characters")
    suspend fun findByName(
        @Query("name") name: String? = null
    ): List<BreakingBadCharacters>

}