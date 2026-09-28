package com.khz.malekcoach.ui.seasons

import com.khz.malekcoach.data.repository.SeasonRepository
import com.khz.malekcoach.domain.model.Season
import com.khz.malekcoach.ui.components.SimpleListViewModel

class SeasonListViewModel(repo: SeasonRepository) :
    SimpleListViewModel<Season>({ repo.getSeasons() })