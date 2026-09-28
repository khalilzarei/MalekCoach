package com.khz.malekcoach.ui.coach

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.khz.malekcoach.MalekCoachApp
import com.khz.malekcoach.core.network.NetworkResult
import com.khz.malekcoach.data.dto.response.CoachClassDto
import com.khz.malekcoach.ui.components.AvatarView
import com.khz.malekcoach.ui.components.GlassCard3D
import com.khz.malekcoach.ui.components.GlassTopBar
import com.khz.malekcoach.ui.theme.GoldPrimary
import kotlinx.coroutines.launch

@Composable
fun CoachClassesScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val repo = (context.applicationContext as MalekCoachApp).container.coachPortalRepository
    var classes by remember { mutableStateOf<List<CoachClassDto>?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var expandedClassId by remember { mutableStateOf<Int?>(null) }
    val scope = androidx.compose.runtime.rememberCoroutineScope()

    suspend fun load() {
        error = null
        when (val result = repo.getClasses()) {
            is NetworkResult.Success -> classes = result.data
            is NetworkResult.Error -> error = result.message
            else -> Unit
        }
    }
    LaunchedEffect(Unit) { load() }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { GlassTopBar(title = "کلاس‌های من", onBack = onBack) }
    ) { padding ->
        when {
            classes == null && error == null -> Box(
                Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator(color = GoldPrimary) }
            error != null && classes == null -> Column(
                Modifier.fillMaxSize().padding(padding).padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(error.orEmpty(), color = Color(0xFFFF8A80))
                androidx.compose.material3.TextButton(onClick = { scope.launch { load() } }) {
                    Text("تلاش دوباره", color = GoldPrimary)
                }
            }
            classes.isNullOrEmpty() -> Box(
                Modifier.fillMaxSize().padding(padding).padding(24.dp),
                contentAlignment = Alignment.Center
            ) { Text("کلاسی به شما تخصیص داده نشده است", color = Color.White.copy(alpha = 0.7f)) }
            else -> LazyColumn(
                Modifier.fillMaxSize().padding(padding),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(classes.orEmpty(), key = { it.id }) { clazz ->
                    val expanded = expandedClassId == clazz.id
                    GlassCard3D(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { expandedClassId = if (expanded) null else clazz.id }
                    ) {
                        Column(Modifier.fillMaxWidth().padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Groups, null, tint = GoldPrimary)
                                Spacer(Modifier.size(10.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(clazz.title, color = Color.White, fontWeight = FontWeight.Bold)
                                    Text(
                                        listOfNotNull(clazz.ageGroupTitle, "${clazz.playerCount} بازیکن").joinToString(" · "),
                                        color = Color.White.copy(alpha = 0.65f),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                Icon(
                                    if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = if (expanded) "بستن فهرست بازیکنان" else "نمایش بازیکنان",
                                    tint = Color.White.copy(alpha = 0.75f)
                                )
                            }
                            if (!clazz.location.isNullOrBlank()) {
                                Text("محل تمرین: ${clazz.location}", color = GoldPrimary, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 8.dp))
                            }
                            if (expanded) {
                                Spacer(Modifier.size(10.dp))
                                if (clazz.players.isEmpty()) {
                                    Text("بازیکنی در این کلاس ثبت‌نام نشده است", color = Color.White.copy(alpha = 0.6f))
                                } else {
                                    clazz.players.forEach { player ->
                                        Row(
                                            Modifier.fillMaxWidth().padding(vertical = 7.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            AvatarView(player.fullName, player.avatarUrl, size = 38.dp)
                                            Spacer(Modifier.size(10.dp))
                                            Column(Modifier.weight(1f)) {
                                                Text(player.fullName, color = Color.White, style = MaterialTheme.typography.bodyMedium)
                                                player.mobile?.takeIf { it.isNotBlank() }?.let {
                                                    Text(it, color = Color.White.copy(alpha = 0.55f), style = MaterialTheme.typography.labelSmall)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
