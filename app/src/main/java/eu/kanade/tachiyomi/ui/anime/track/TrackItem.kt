package eu.kanade.tachiyomi.ui.anime.track

import eu.kanade.tachiyomi.data.track.Tracker
import tachiyomi.domain.track.model.AnimeTrack

data class TrackItem(val track: AnimeTrack?, val tracker: Tracker)
