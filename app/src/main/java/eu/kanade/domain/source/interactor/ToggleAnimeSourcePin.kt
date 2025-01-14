package eu.kanade.domain.source.interactor

import eu.kanade.domain.source.service.SourcePreferences
import tachiyomi.core.common.preference.getAndSet
import tachiyomi.domain.source.model.AnimeSource

class ToggleAnimeSourcePin(
    private val preferences: SourcePreferences,
) {

    fun await(source: AnimeSource) {
        val isPinned = source.id.toString() in preferences.pinnedAnimeSources().get()
        preferences.pinnedAnimeSources().getAndSet { pinned ->
            if (isPinned) pinned.minus("${source.id}") else pinned.plus("${source.id}")
        }
    }
}
