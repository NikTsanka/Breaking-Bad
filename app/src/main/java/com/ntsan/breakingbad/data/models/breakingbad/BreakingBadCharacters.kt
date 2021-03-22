package com.ntsan.breakingbad.data.models.breakingbad


import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class BreakingBadCharacters(
    @Json(name = "appearance")
    val appearance: List<Int>,
    @Json(name = "better_call_saul_appearance")
    val betterCallSaulAppearance: List<Any>,
    @Json(name = "birthday")
    val birthday: String,
    @Json(name = "category")
    val category: String,
    @Json(name = "char_id")
    val charId: Int,
    @Json(name = "img")
    val img: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "nickname")
    val nickname: String,
    @Json(name = "occupation")
    val occupation: List<String>,
    @Json(name = "portrayed")
    val portrayed: String,
    @Json(name = "status")
    val status: String
)