package com.ntsan.breakingbad.data.models.user


import com.squareup.moshi.Json
import androidx.annotation.Keep

@Keep
data class UserProfile(
    @Json(name = "imageUrl")
    val imageUrl: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "userName")
    val userName: String
)