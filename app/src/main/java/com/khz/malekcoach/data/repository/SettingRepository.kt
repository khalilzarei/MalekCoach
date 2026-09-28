package com.khz.malekcoach.data.repository

import com.khz.malekcoach.core.network.NetworkResult
import com.khz.malekcoach.core.network.safeCall
import com.khz.malekcoach.data.dto.request.SettingItemRequest
import com.khz.malekcoach.data.dto.request.UpdateSettingsRequest
import com.khz.malekcoach.data.remote.SettingApi
import com.khz.malekcoach.domain.mapper.toDomain
import com.khz.malekcoach.domain.model.Setting

class SettingRepository(private val api: SettingApi) {
    suspend fun getSettings(): NetworkResult<List<Setting>> =
        safeCall({ api.getSettings() }) { it.map { d -> d.toDomain() } }

    suspend fun updateSettings(items: List<SettingItemRequest>): NetworkResult<List<Setting>> =
        safeCall({ api.updateSettings(UpdateSettingsRequest(items)) }) { it.map { d -> d.toDomain() } }
}