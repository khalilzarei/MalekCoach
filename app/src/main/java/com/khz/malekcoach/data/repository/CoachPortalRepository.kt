package com.khz.malekcoach.data.repository

import com.khz.malekcoach.core.network.NetworkResult
import com.khz.malekcoach.data.dto.response.CoachClassDto
import com.khz.malekcoach.data.remote.ClientApi

class CoachPortalRepository(private val api: ClientApi) {
    suspend fun getClasses(): NetworkResult<List<CoachClassDto>> = try {
        val response = api.getMyClasses()
        if (response.success && response.data != null) {
            NetworkResult.Success(response.data.classes)
        } else {
            NetworkResult.Error(response.message ?: "دریافت کلاس‌های مربی انجام نشد")
        }
    } catch (e: Exception) {
        NetworkResult.Error(e.message ?: "ارتباط با سرور برقرار نشد")
    }
}
