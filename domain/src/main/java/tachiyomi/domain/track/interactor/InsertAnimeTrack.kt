package tachiyomi.domain.track.interactor

import logcat.LogPriority
import tachiyomi.core.common.util.system.logcat
import tachiyomi.domain.track.model.AnimeTrack
import tachiyomi.domain.track.repository.AnimeTrackRepository

class InsertAnimeTrack(
    private val animetrackRepository: AnimeTrackRepository,
) {

    suspend fun await(track: AnimeTrack) {
        try {
            animetrackRepository.insertAnime(track)
        } catch (e: Exception) {
            logcat(LogPriority.ERROR, e)
        }
    }

    suspend fun awaitAll(tracks: List<AnimeTrack>) {
        try {
            animetrackRepository.insertAllAnime(tracks)
        } catch (e: Exception) {
            logcat(LogPriority.ERROR, e)
        }
    }
}
