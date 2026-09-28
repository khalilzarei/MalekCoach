package com.khz.malekcoach.ui.discounts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.khz.malekcoach.core.util.appViewModel
import com.khz.malekcoach.ui.components.GenericListScreen
import com.khz.malekcoach.ui.components.InfoCard

@Composable
fun DiscountListScreen(onBack: () -> Unit) {
    val vm: DiscountListViewModel = appViewModel()
    val state by vm.state.collectAsState()

    GenericListScreen(
        title = "تخفیف‌ها",
        state = state,
        onRefresh = vm::refresh,
        onBack = onBack
    ) { d ->
        val valueText = if (d.discountType == "percent") "${d.value.toInt()}٪" else "${d.value.toInt()} تومان"
        InfoCard(
            title = d.title,
            subtitle = "نوع: $valueText",
            trailing = if (d.isActive) "فعال" else "غیرفعال"
        )
    }
}