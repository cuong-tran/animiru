package eu.kanade.tachiyomi.animesource

import eu.kanade.tachiyomi.source.Source

/**
 * A factory for creating sources at runtime.
 */
interface AnimeSourceFactory {
    /**
     * Create a new copy of the sources
     * @return The created sources
     */
    fun createSources(): List<Source>
}
