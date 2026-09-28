package com.khz.malekcoach.data.repository

import com.khz.malekcoach.core.network.NetworkResult
import com.khz.malekcoach.core.network.safeCall
import com.khz.malekcoach.data.dto.request.SendNotificationRequest
import com.khz.malekcoach.data.remote.NotificationApi
import com.khz.malekcoach.domain.mapper.toDomain
import com.khz.malekcoach.domain.model.Notification

class NotificationRepository(private val api: NotificationApi) {
    suspend fun getNotifications(page: Int = 1, perPage: Int = 20, unreadOnly: Boolean? = null, type: String? = null): NetworkResult<List<Notification>> =
        safeCall({ api.getNotifications(page, perPage, unreadOnly, type) }) { it.items.map { d -> d.toDomain() } }

    suspend fun getUnreadCount(): NetworkResult<Int> =
        safeCall({ api.getUnreadCount() }) { it.unreadCount }

    suspend fun markAsRead(id: Int): NetworkResult<Unit> = safeCall({ api.markAsRead(id) }) { }
    suspend fun markAllAsRead(): NetworkResult<Unit> = safeCall({ api.markAllAsRead() }) { }
    suspend fun send(request: SendNotificationRequest): NetworkResult<Unit> = safeCall({ api.send(request) }) { }
}