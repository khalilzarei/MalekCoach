package com.khz.malekcoach.data.repository

import com.khz.malekcoach.core.network.NetworkResult
import com.khz.malekcoach.core.network.safeCall
import com.khz.malekcoach.data.remote.ClientApi
import com.khz.malekcoach.domain.mapper.toDomain
import com.khz.malekcoach.domain.model.Media
import com.khz.malekcoach.domain.model.MyChild
import com.khz.malekcoach.domain.model.MyFinance
import com.khz.malekcoach.domain.model.MyScheduleItem
import com.khz.malekcoach.domain.model.News

class ClientRepository(private val api: ClientApi) {

    suspend fun getMyChildren(): NetworkResult<List<MyChild>> = safeCall({ api.getMyChildren() }) {
        it.children.orEmpty()
            .map { d -> d.toDomain() }
    }

    suspend fun getMySchedule(): NetworkResult<List<MyScheduleItem>> = safeCall({ api.getMySchedule() }) {
        it.sessions.orEmpty()
            .map { d -> d.toDomain() }
    }

    suspend fun getMyNews(): NetworkResult<List<News>> = safeCall({ api.getMyNews() }) {
        it.news.orEmpty()
            .map { d -> d.toDomain() }
    }

    suspend fun getMyMedia(): NetworkResult<List<Media>> = safeCall({ api.getMyMedia() }) {
        it.media.orEmpty()
            .map { d -> d.toDomain() }
    }

    suspend fun getMyFinance(): NetworkResult<List<MyFinance>> = safeCall({ api.getMyFinance() }) {
        it.finance.orEmpty()
            .map { d -> d.toDomain() }
    }
}