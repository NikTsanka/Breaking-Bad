package com.ntsan.breakingbad.data.models


import com.squareup.moshi.Json
import androidx.annotation.Keep

@Keep
data class UserSession(
    @Json(name = "accessToken")
    val accessToken: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "userName")
    val userName: String
)