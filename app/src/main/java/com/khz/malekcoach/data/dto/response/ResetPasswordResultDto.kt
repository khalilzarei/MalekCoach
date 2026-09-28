package com.khz.malekcoach.data.dto.response

import com.google.gson.annotations.SerializedName

data class ResetPasswordResultDto(
    @SerializedName(value = "new_password", alternate = ["password"])
    val password: String
)
