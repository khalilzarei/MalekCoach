package com.khz.malekcoach.ui.matches

import com.khz.malekcoach.data.repository.MatchRepository
import com.khz.malekcoach.domain.model.Match
import com.khz.malekcoach.ui.components.SimpleListViewModel

class MatchListViewModel(repo: MatchRepository) :
    SimpleListViewModel<Match>({ repo.getMatches() })