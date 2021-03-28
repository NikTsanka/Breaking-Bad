package com.ntsan.breakingbad.data.models.breakingbad

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class Data<T>(
    @Json(name = "data")
    val data: T
)