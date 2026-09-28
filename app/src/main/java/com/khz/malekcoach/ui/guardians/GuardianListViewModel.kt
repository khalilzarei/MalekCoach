package com.khz.malekcoach.ui.guardians

import com.khz.malekcoach.data.repository.GuardianRepository
import com.khz.malekcoach.domain.model.Guardian
import com.khz.malekcoach.ui.components.SimpleListViewModel

class GuardianListViewModel(repo: GuardianRepository) :
    SimpleListViewModel<Guardian>({ repo.getGuardians() })