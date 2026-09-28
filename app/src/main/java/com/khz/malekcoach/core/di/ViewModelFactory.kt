package com.khz.malekcoach.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khz.malekcoach.ui.age_groups.AgeGroupListViewModel
import com.khz.malekcoach.ui.attendance.AttendanceViewModel
import com.khz.malekcoach.ui.auth.AuthViewModel
import com.khz.malekcoach.ui.chat.ChatRoomListViewModel
import com.khz.malekcoach.ui.chat.ChatViewModel
import com.khz.malekcoach.ui.classes.ClassFormViewModel
import com.khz.malekcoach.ui.classes.ClassListViewModel
import com.khz.malekcoach.ui.dashboard.DashboardViewModel
import com.khz.malekcoach.ui.discounts.DiscountListViewModel
import com.khz.malekcoach.ui.guardians.GuardianListViewModel
import com.khz.malekcoach.ui.invoices.InvoiceListViewModel
import com.khz.malekcoach.ui.matches.MatchListViewModel
import com.khz.malekcoach.ui.media.MediaListViewModel
import com.khz.malekcoach.ui.news.NewsDetailViewModel
import com.khz.malekcoach.ui.news.NewsFormViewModel
import com.khz.malekcoach.ui.news.NewsListViewModel
import com.khz.malekcoach.ui.notifications.NotificationListViewModel
import com.khz.malekcoach.ui.payments.PaymentListViewModel
import com.khz.malekcoach.ui.players.PlayerDetailViewModel
import com.khz.malekcoach.ui.players.PlayerListViewModel
import com.khz.malekcoach.ui.reports.ReportsViewModel
import com.khz.malekcoach.ui.seasons.SeasonListViewModel
import com.khz.malekcoach.ui.sessions.SessionListViewModel
import com.khz.malekcoach.ui.settings.SettingsViewModel
import com.khz.malekcoach.ui.users.UserDetailViewModel
import com.khz.malekcoach.ui.users.UserListViewModel

class ViewModelFactory(private val container: AppContainer) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(AuthViewModel::class.java)             -> AuthViewModel(container.authRepository) as T
        modelClass.isAssignableFrom(DashboardViewModel::class.java)        -> DashboardViewModel(container.reportRepository) as T

        modelClass.isAssignableFrom(UserListViewModel::class.java)         -> UserListViewModel(container.userRepository) as T
        modelClass.isAssignableFrom(UserDetailViewModel::class.java)       -> UserDetailViewModel(container.userRepository) as T

        modelClass.isAssignableFrom(PlayerListViewModel::class.java)       -> PlayerListViewModel(container.playerRepository) as T
        modelClass.isAssignableFrom(PlayerDetailViewModel::class.java)     -> PlayerDetailViewModel(
            container.playerRepository,
            container.userRepository
        ) as T
        modelClass.isAssignableFrom(ClassFormViewModel::class.java)        -> {
            ClassFormViewModel(
                container.classRepository,
                container.ageGroupRepository,
                container.coachRepository
            ) as T
        }

        modelClass.isAssignableFrom(GuardianListViewModel::class.java)     -> GuardianListViewModel(container.guardianRepository) as T
        modelClass.isAssignableFrom(SeasonListViewModel::class.java)       -> SeasonListViewModel(container.seasonRepository) as T
        modelClass.isAssignableFrom(AgeGroupListViewModel::class.java)     -> AgeGroupListViewModel(container.ageGroupRepository) as T
        modelClass.isAssignableFrom(ClassListViewModel::class.java)        -> ClassListViewModel(container.classRepository) as T
        modelClass.isAssignableFrom(SessionListViewModel::class.java)      -> SessionListViewModel(
            container.sessionRepository,
            container.classRepository
        ) as T

        modelClass.isAssignableFrom(AttendanceViewModel::class.java)       -> AttendanceViewModel(
            container.sessionRepository,
            container.classRepository
        ) as T

        modelClass.isAssignableFrom(InvoiceListViewModel::class.java)      -> InvoiceListViewModel(container.invoiceRepository) as T
        modelClass.isAssignableFrom(DiscountListViewModel::class.java)     -> DiscountListViewModel(container.discountRepository) as T
        modelClass.isAssignableFrom(PaymentListViewModel::class.java)      -> PaymentListViewModel(container.paymentRepository) as T
        modelClass.isAssignableFrom(MediaListViewModel::class.java)        -> MediaListViewModel(container.mediaRepository) as T
        modelClass.isAssignableFrom(NewsListViewModel::class.java)         -> NewsListViewModel(container.newsRepository) as T
        modelClass.isAssignableFrom(NewsDetailViewModel::class.java)       -> NewsDetailViewModel(container.newsRepository) as T
        modelClass.isAssignableFrom(NewsFormViewModel::class.java)         -> NewsFormViewModel(
            container.newsRepository,
            container.mediaRepository,
            container.ageGroupRepository,
            container.classRepository
        ) as T

        modelClass.isAssignableFrom(MatchListViewModel::class.java)        -> MatchListViewModel(container.matchRepository) as T
        modelClass.isAssignableFrom(NotificationListViewModel::class.java) -> NotificationListViewModel(container.notificationRepository) as T
        modelClass.isAssignableFrom(SettingsViewModel::class.java)         -> SettingsViewModel(container.settingRepository) as T
        modelClass.isAssignableFrom(ChatRoomListViewModel::class.java)     -> ChatRoomListViewModel(container.chatRepository) as T
        modelClass.isAssignableFrom(ChatViewModel::class.java)             -> ChatViewModel(container.chatRepository) as T
        modelClass.isAssignableFrom(ReportsViewModel::class.java)          -> ReportsViewModel(container.reportRepository) as T
        else                                                               -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
    }
}
