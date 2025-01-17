package eu.kanade.domain.extension.model

import eu.kanade.tachiyomi.extension.model.AnimeExtension

data class Extensions(
    val updates: List<AnimeExtension.Installed>,
    val installed: List<AnimeExtension.Installed>,
    val available: List<AnimeExtension.Available>,
    val untrusted: List<AnimeExtension.Untrusted>,
)
