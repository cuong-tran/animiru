package tachiyomi.domain.track.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tachiyomi.domain.track.model.AnimeTrack
import tachiyomi.domain.track.repository.AnimeTrackRepository

class GetTracksPerAnime(
    private val trackRepository: AnimeTrackRepository,
) {

    fun subscribe(): Flow<Map<Long, List<AnimeTrack>>> {
        return trackRepository.getAnimeTracksAsFlow().map { tracks -> tracks.groupBy { it.animeId } }
    }
}
