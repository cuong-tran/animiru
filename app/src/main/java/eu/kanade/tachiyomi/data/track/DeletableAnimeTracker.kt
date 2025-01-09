package eu.kanade.tachiyomi.data.track

import tachiyomi.domain.track.model.AnimeTrack

/**
 *Tracker that support deleting am entry from a user's list
 */
interface DeletableAnimeTracker {

    suspend fun delete(track: AnimeTrack)
}
