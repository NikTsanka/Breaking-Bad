package com.ntsan.breakingbad.data.models.breakingbad


import android.os.Parcelable
import com.squareup.moshi.Json
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class BreakingBadEpisodes(
    @Json(name = "air_date")
    val airDate: String,
    @Json(name = "characters")
    val characters: List<String>,
    @Json(name = "episode")
    val episode: String,
    @Json(name = "episode_id")
    val episodeId: Int,
    @Json(name = "season")
    val season: String,
    @Json(name = "series")
    val series: String,
    @Json(name = "title")
    val title: String
): Parcelable