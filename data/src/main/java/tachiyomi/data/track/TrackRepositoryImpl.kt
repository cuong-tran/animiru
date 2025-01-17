package tachiyomi.data.track

import kotlinx.coroutines.flow.Flow
import tachiyomi.data.AnimeDatabaseHandler
import tachiyomi.data.track.AnimeTrackMapper.mapTrack
import tachiyomi.domain.track.model.Track
import tachiyomi.domain.track.repository.TrackRepository

class TrackRepositoryImpl(
    private val handler: AnimeDatabaseHandler,
) : TrackRepository {

    override suspend fun getTrackByAnimeId(id: Long): Track? {
        return handler.awaitOneOrNull { anime_syncQueries.getTrackByAnimeId(id, AnimeTrackMapper::mapTrack) }
    }

    override suspend fun getTracksByAnimeId(animeId: Long): List<Track> {
        return handler.awaitList {
            anime_syncQueries.getTracksByAnimeId(animeId, AnimeTrackMapper::mapTrack)
        }
    }

    override fun getAnimeTracksAsFlow(): Flow<List<Track>> {
        return handler.subscribeToList {
            anime_syncQueries.getAnimeTracks(AnimeTrackMapper::mapTrack)
        }
    }

    override fun getTracksByAnimeIdAsFlow(animeId: Long): Flow<List<Track>> {
        return handler.subscribeToList {
            anime_syncQueries.getTracksByAnimeId(animeId, AnimeTrackMapper::mapTrack)
        }
    }

    override suspend fun delete(animeId: Long, trackerId: Long) {
        handler.await {
            anime_syncQueries.delete(
                animeId = animeId,
                syncId = trackerId,
            )
        }
    }

    override suspend fun insertAnime(track: Track) {
        insertValues(track)
    }

    override suspend fun insertAllAnime(tracks: List<Track>) {
        insertValues(*tracks.toTypedArray())
    }

    private suspend fun insertValues(vararg tracks: Track) {
        handler.await(inTransaction = true) {
            tracks.forEach { animeTrack ->
                anime_syncQueries.insert(
                    animeId = animeTrack.animeId,
                    syncId = animeTrack.trackerId,
                    remoteId = animeTrack.remoteId,
                    libraryId = animeTrack.libraryId,
                    title = animeTrack.title,
                    lastEpisodeSeen = animeTrack.lastEpisodeSeen,
                    totalEpisodes = animeTrack.totalEpisodes,
                    status = animeTrack.status,
                    score = animeTrack.score,
                    remoteUrl = animeTrack.remoteUrl,
                    startDate = animeTrack.startDate,
                    finishDate = animeTrack.finishDate,
                )
            }
        }
    }

    // AM (GROUPING) -->
    override suspend fun getTracks(): List<Track> {
        return handler.awaitList {
            anime_syncQueries.getAnimeTracks(::mapTrack)
        }
    }
    // <-- AM (GROUPING)
}
