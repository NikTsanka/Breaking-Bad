package com.ntsan.breakingbad.data.models.breakingbad


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

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