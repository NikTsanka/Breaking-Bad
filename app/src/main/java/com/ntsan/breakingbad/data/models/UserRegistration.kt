package com.ntsan.breakingbad.data.models


import com.squareup.moshi.Json
import androidx.annotation.Keep

@Keep
data class UserRegistration(
    @Json(name = "name")
    val name: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "userName")
    val userName: String
)