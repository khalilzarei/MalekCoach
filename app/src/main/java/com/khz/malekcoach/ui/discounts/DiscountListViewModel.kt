package com.khz.malekcoach.ui.discounts

import com.khz.malekcoach.data.repository.DiscountRepository
import com.khz.malekcoach.domain.model.Discount
import com.khz.malekcoach.ui.components.SimpleListViewModel

class DiscountListViewModel(repo: DiscountRepository) :
    SimpleListViewModel<Discount>({ repo.getDiscounts() })