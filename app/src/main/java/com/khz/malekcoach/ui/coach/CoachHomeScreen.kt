package com.khz.malekcoach.ui.coach

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.khz.malekcoach.ui.components.GlassCard3D
import com.khz.malekcoach.ui.components.GlassTopBar
import com.khz.malekcoach.ui.theme.GoldPrimary

@Composable
fun CoachHomeScreen(
    onClasses: () -> Unit,
    onSessions: () -> Unit,
    onChats: () -> Unit,
    onSignOut: () -> Unit
) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            GlassTopBar(
                title = "پنل مربی",
                actions = {
                    IconButton(onClick = onSignOut) {
                        Icon(Icons.Default.Logout, contentDescription = "خروج از حساب", tint = Color.White.copy(alpha = 0.85f))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                "مدیریت کلاس‌ها، پیگیری جلسات و گفتگو با بازیکنان",
                color = Color.White.copy(alpha = 0.72f),
                style = MaterialTheme.typography.bodyMedium
            )
            CoachHomeItem("کلاس‌های من", "فهرست کلاس‌ها و بازیکنان هر کلاس", Icons.Default.Groups, onClasses)
            CoachHomeItem("جلسات و عملکرد", "حضور و غیاب و یادداشت عملکرد بازیکنان", Icons.Default.EventNote, onSessions)
            CoachHomeItem("گفتگوها", "چت کلاس‌ها و گفتگوی همگانی", Icons.Default.Chat, onChats)
        }
    }
}

@Composable
private fun CoachHomeItem(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    GlassCard3D(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        androidx.compose.foundation.layout.Row(
            Modifier.fillMaxWidth().padding(18.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = GoldPrimary)
            androidx.compose.foundation.layout.Column(Modifier.weight(1f)) {
                Text(title, color = Color.White, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(subtitle, color = Color.White.copy(alpha = 0.65f), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
