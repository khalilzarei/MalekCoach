package com.khz.malekcoach.ui.notifications

import com.khz.malekcoach.data.repository.NotificationRepository
import com.khz.malekcoach.domain.model.Notification
import com.khz.malekcoach.ui.components.SimpleListViewModel

class NotificationListViewModel(repo: NotificationRepository) :
    SimpleListViewModel<Notification>({ repo.getNotifications() })