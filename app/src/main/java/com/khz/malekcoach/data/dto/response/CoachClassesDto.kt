package com.khz.malekcoach.data.dto.response

import com.google.gson.annotations.SerializedName

data class CoachClassesResponseDto(
    @SerializedName("classes") val classes: List<CoachClassDto> = emptyList()
)

data class CoachClassDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("age_group_title") val ageGroupTitle: String? = null,
    @SerializedName("location") val location: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("player_count") val playerCount: Int = 0,
    @SerializedName("players") val players: List<CoachClassPlayerDto> = emptyList()
)

data class CoachClassPlayerDto(
    @SerializedName("id") val id: Int,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("mobile") val mobile: String? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null
)
