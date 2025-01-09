package tachiyomi.domain.source.repository

import kotlinx.coroutines.flow.Flow
import tachiyomi.domain.source.model.StubAnimeSource

interface AnimeStubSourceRepository {
    fun subscribeAllAnime(): Flow<List<StubAnimeSource>>

    suspend fun getStubAnimeSource(id: Long): StubAnimeSource?

    suspend fun upsertStubAnimeSource(id: Long, lang: String, name: String)
}
