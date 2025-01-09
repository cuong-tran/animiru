package tachiyomi.domain.source.repository

import androidx.paging.PagingSource
import eu.kanade.tachiyomi.animesource.model.AnimeFilterList
import eu.kanade.tachiyomi.animesource.model.SAnime
import kotlinx.coroutines.flow.Flow
import tachiyomi.domain.source.model.AnimeSource
import tachiyomi.domain.source.model.AnimeSourceWithCount

typealias AnimeSourcePagingSourceType = PagingSource<Long, SAnime>

interface AnimeSourceRepository {

    fun getAnimeSources(): Flow<List<AnimeSource>>

    fun getOnlineAnimeSources(): Flow<List<AnimeSource>>

    fun getAnimeSourcesWithFavoriteCount(): Flow<List<Pair<AnimeSource, Long>>>

    fun getSourcesWithNonLibraryAnime(): Flow<List<AnimeSourceWithCount>>

    fun searchAnime(sourceId: Long, query: String, filterList: AnimeFilterList): AnimeSourcePagingSourceType

    fun getPopularAnime(sourceId: Long): AnimeSourcePagingSourceType

    fun getLatestAnime(sourceId: Long): AnimeSourcePagingSourceType
}
