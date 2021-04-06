package com.ntsan.breakingbad.data.models.breakingbad


import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class BreakingBadQuotes(
    @Json(name = "author")
    val author: String,
    @Json(name = "quote")
    val quote: String,
    @Json(name = "quote_id")
    val quoteId: Int,
    @Json(name = "series")
    val series: String
)