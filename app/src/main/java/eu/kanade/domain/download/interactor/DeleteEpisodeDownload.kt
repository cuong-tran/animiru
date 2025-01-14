package eu.kanade.domain.download.interactor

import eu.kanade.tachiyomi.data.download.AnimeDownloadManager
import tachiyomi.core.common.util.lang.withNonCancellableContext
import tachiyomi.domain.anime.model.Anime
import tachiyomi.domain.episode.model.Episode
import tachiyomi.domain.source.service.AnimeSourceManager

class DeleteEpisodeDownload(
    private val sourceManager: AnimeSourceManager,
    private val downloadManager: AnimeDownloadManager,
) {

    suspend fun awaitAll(anime: Anime, vararg episodes: Episode) = withNonCancellableContext {
        sourceManager.get(anime.source)?.let { source ->
            downloadManager.deleteEpisodes(episodes.toList(), anime, source)
        }
    }
}
