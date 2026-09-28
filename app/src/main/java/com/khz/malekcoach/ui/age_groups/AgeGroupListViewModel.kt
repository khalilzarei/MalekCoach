package com.khz.malekcoach.ui.age_groups

import com.khz.malekcoach.data.repository.AgeGroupRepository
import com.khz.malekcoach.domain.model.AgeGroup
import com.khz.malekcoach.ui.components.SimpleListViewModel

class AgeGroupListViewModel(repo: AgeGroupRepository) :
    SimpleListViewModel<AgeGroup>({ repo.getAgeGroups() })