package eu.kanade.domain.track.interactor

import eu.kanade.domain.track.model.toDbTrack
import eu.kanade.domain.track.model.toDomainTrack
import eu.kanade.tachiyomi.data.track.Tracker
import eu.kanade.tachiyomi.data.track.TrackerManager
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import tachiyomi.domain.track.interactor.GetAnimeTracks
import tachiyomi.domain.track.interactor.InsertAnimeTrack

class RefreshTracks(
    private val getTracks: GetAnimeTracks,
    private val trackerManager: TrackerManager,
    private val insertTrack: InsertAnimeTrack,
    private val syncEpisodeProgressWithTrack: SyncEpisodeProgressWithTrack,
) {

    /**
     * Fetches updated tracking data from all logged in trackers.
     *
     * @return Failed updates.
     */
    suspend fun await(animeId: Long): List<Pair<Tracker?, Throwable>> {
        return supervisorScope {
            return@supervisorScope getTracks.await(animeId)
                .map { it to trackerManager.get(it.trackerId) }
                .filter { (_, service) -> service?.isLoggedIn == true }
                .map { (track, service) ->
                    async {
                        return@async try {
                            val updatedTrack = service!!.animeService.refresh(track.toDbTrack()).toDomainTrack()!!
                            insertTrack.await(updatedTrack)
                            syncEpisodeProgressWithTrack.await(animeId, updatedTrack, service.animeService)
                            null
                        } catch (e: Throwable) {
                            service to e
                        }
                    }
                }
                .awaitAll()
                .filterNotNull()
        }
    }
}
