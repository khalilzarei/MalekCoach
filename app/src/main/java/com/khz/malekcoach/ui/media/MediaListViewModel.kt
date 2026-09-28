package com.khz.malekcoach.ui.media

import com.khz.malekcoach.data.repository.MediaRepository
import com.khz.malekcoach.domain.model.Media
import com.khz.malekcoach.ui.components.SimpleListViewModel

class MediaListViewModel(repo: MediaRepository) :
    SimpleListViewModel<Media>({ repo.getMedia() })