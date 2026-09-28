package com.khz.malekcoach.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.khz.malekcoach.ui.theme.GoldPrimary
import com.khz.malekcoach.ui.theme.DesignTokens

@Composable
fun GlassSectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        title,
        modifier = modifier.padding(horizontal = DesignTokens.ContentInset),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = GoldPrimary
    )
}
