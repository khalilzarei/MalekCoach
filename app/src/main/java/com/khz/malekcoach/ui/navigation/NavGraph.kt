package com.khz.malekcoach.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.khz.malekcoach.MalekCoachApp
import com.khz.malekcoach.core.di.ViewModelFactory
import com.khz.malekcoach.core.network.NetworkResult
import com.khz.malekcoach.ui.attendance.AttendanceScreen
import com.khz.malekcoach.ui.auth.ChangePasswordScreen
import com.khz.malekcoach.ui.auth.LoginScreen
import com.khz.malekcoach.ui.chat.ChatContactsScreen
import com.khz.malekcoach.ui.chat.ChatRoomListScreen
import com.khz.malekcoach.ui.chat.ChatScreen
import com.khz.malekcoach.ui.coach.CoachClassesScreen
import com.khz.malekcoach.ui.coach.CoachHomeScreen
import com.khz.malekcoach.ui.components.NetworkStatusBanner
import com.khz.malekcoach.ui.sessions.MySessionsScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private object CoachRoute {
    const val HOME = "coach_home"
    const val CLASSES = "coach_classes"
    const val SESSIONS = "coach_sessions"
    const val CHATS = "coach_chats"
    const val CONTACTS = "coach_contacts"
    const val CHAT = "coach_chat/{roomId}"
    const val ATTENDANCE = "coach_attendance/{sessionId}/{classId}"
}

@Composable
fun AppNavigation(viewModelFactory: ViewModelFactory) {
    val nav = rememberNavController()
    val context = LocalContext.current
    val app = context.applicationContext as MalekCoachApp
    val container = app.container
    val scope = rememberCoroutineScope()
    var startDestination by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val keepSignedIn = container.sessionManager.rememberMe.first()
        val token = container.sessionManager.authToken.first()
        val role = container.sessionManager.userRole.first()
        if (keepSignedIn && !token.isNullOrBlank() && role == "coach") {
            startDestination = CoachRoute.HOME
        } else {
            if (!token.isNullOrBlank() || (role != null && role != "coach")) {
                container.sessionManager.clearSession()
            }
            startDestination = "login"
        }
    }

    val start = startDestination ?: return
    Box(Modifier.fillMaxSize()) {
        NavHost(navController = nav, startDestination = start) {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        nav.navigate(CoachRoute.HOME) {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onRequirePasswordChange = {
                        nav.navigate("coach_change_password") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
            composable("coach_change_password") {
                ChangePasswordScreen(onPasswordChanged = {
                    nav.navigate(CoachRoute.HOME) {
                        popUpTo("coach_change_password") { inclusive = true }
                    }
                })
            }
            composable(CoachRoute.HOME) {
                CoachHomeScreen(
                    onClasses = { nav.navigate(CoachRoute.CLASSES) },
                    onSessions = { nav.navigate(CoachRoute.SESSIONS) },
                    onChats = { nav.navigate(CoachRoute.CHATS) },
                    onSignOut = {
                        scope.launch {
                            container.authRepository.logout()
                            nav.navigate("login") {
                                popUpTo(CoachRoute.HOME) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
            composable(CoachRoute.CLASSES) {
                CoachClassesScreen(onBack = { nav.popBackStack() })
            }
            composable(CoachRoute.SESSIONS) {
                MySessionsScreen(
                    onBack = { nav.popBackStack() },
                    coachMode = true,
                    onSessionClick = { sessionId, classId ->
                        nav.navigate("coach_attendance/$sessionId/$classId")
                    }
                )
            }
            composable(CoachRoute.CHATS) {
                ChatRoomListScreen(
                    onBack = { nav.popBackStack() },
                    onOpenChat = { nav.navigate("coach_chat/$it") },
                    onOpenContacts = { nav.navigate(CoachRoute.CONTACTS) },
                    onOpenCreateGroup = {},
                    coachMode = true
                )
            }
            composable(CoachRoute.CONTACTS) {
                ChatContactsScreen(
                    onBack = { nav.popBackStack() },
                    onPick = { targetUserId ->
                        scope.launch {
                            when (val result = container.chatRepository.createPrivateRoom(targetUserId)) {
                                is NetworkResult.Success -> nav.navigate("coach_chat/${result.data.id}")
                                else -> Unit
                            }
                        }
                    }
                )
            }
            composable(
                CoachRoute.CHAT,
                arguments = listOf(navArgument("roomId") { type = NavType.IntType })
            ) { entry ->
                val roomId = entry.arguments?.getInt("roomId") ?: return@composable
                ChatScreen(roomId = roomId, onBack = { nav.popBackStack() })
            }
            composable(
                CoachRoute.ATTENDANCE,
                arguments = listOf(
                    navArgument("sessionId") { type = NavType.IntType },
                    navArgument("classId") { type = NavType.IntType }
                )
            ) { entry ->
                val sessionId = entry.arguments?.getInt("sessionId") ?: return@composable
                val classId = entry.arguments?.getInt("classId") ?: return@composable
                AttendanceScreen(
                    sessionId = sessionId,
                    classId = classId,
                    onBack = { nav.popBackStack() }
                )
            }
        }
        NetworkStatusBanner()
    }
}
