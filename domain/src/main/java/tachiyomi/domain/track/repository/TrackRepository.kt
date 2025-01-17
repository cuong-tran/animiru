package tachiyomi.domain.track.repository

import kotlinx.coroutines.flow.Flow
import tachiyomi.domain.track.model.Track

interface TrackRepository {

    suspend fun getTrackByAnimeId(id: Long): Track?

    suspend fun getTracksByAnimeId(animeId: Long): List<Track>

    fun getAnimeTracksAsFlow(): Flow<List<Track>>

    fun getTracksByAnimeIdAsFlow(animeId: Long): Flow<List<Track>>

    suspend fun delete(animeId: Long, trackerId: Long)

    suspend fun insertAnime(track: Track)

    suspend fun insertAllAnime(tracks: List<Track>)

    // AM (GROUPING) -->
    suspend fun getTracks(): List<Track>
    // <-- AM (GROUPING)
}
