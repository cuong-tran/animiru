package tachiyomi.data.source

import kotlinx.coroutines.flow.Flow
import tachiyomi.data.AnimeDatabaseHandler
import tachiyomi.domain.source.model.StubSource
import tachiyomi.domain.source.repository.StubSourceRepository

class StubSourceRepositoryImpl(
    private val handler: AnimeDatabaseHandler,
) : StubSourceRepository {

    override fun subscribeAllAnime(): Flow<List<StubSource>> {
        return handler.subscribeToList { animesourcesQueries.findAll(::mapStubSource) }
    }

    override suspend fun getStubAnimeSource(id: Long): StubSource? {
        return handler.awaitOneOrNull {
            animesourcesQueries.findOne(
                id,
                ::mapStubSource,
            )
        }
    }

    override suspend fun upsertStubAnimeSource(id: Long, lang: String, name: String) {
        handler.await { animesourcesQueries.upsert(id, lang, name) }
    }

    private fun mapStubSource(
        id: Long,
        lang: String,
        name: String,
    ): StubSource = StubSource(id = id, lang = lang, name = name)
}
