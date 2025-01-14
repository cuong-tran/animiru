package eu.kanade.presentation.history.components

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tachiyomi.domain.anime.model.AnimeCover
import tachiyomi.domain.history.model.AnimeHistoryWithRelations
import java.util.Date

internal class AnimeHistoryWithRelationsProvider : PreviewParameterProvider<AnimeHistoryWithRelations> {

    private val simple = AnimeHistoryWithRelations(
        id = 1L,
        episodeId = 2L,
        animeId = 3L,
        // AM (CUSTOM_INFORMATION) -->
        ogTitle = "Test Title",
        // <-- AM (CUSTOM_INFORMATION)
        episodeNumber = 10.2,
        seenAt = Date(1697247357L),
        coverData = AnimeCover(
            animeId = 3L,
            sourceId = 4L,
            isAnimeFavorite = false,
            url = "https://example.com/cover.png",
            lastModified = 5L,
        ),
    )

    private val historyWithoutReadAt = AnimeHistoryWithRelations(
        id = 1L,
        episodeId = 2L,
        animeId = 3L,
        // AM (CUSTOM_INFORMATION) -->
        ogTitle = "Test Title",
        // <-- AM (CUSTOM_INFORMATION)
        episodeNumber = 10.2,
        seenAt = null,
        coverData = AnimeCover(
            animeId = 3L,
            sourceId = 4L,
            isAnimeFavorite = false,
            url = "https://example.com/cover.png",
            lastModified = 5L,
        ),
    )

    private val historyWithNegativeChapterNumber = AnimeHistoryWithRelations(
        id = 1L,
        episodeId = 2L,
        animeId = 3L,
        // AM (CUSTOM_INFORMATION) -->
        ogTitle = "Test Title",
        // <-- AM (CUSTOM_INFORMATION)
        episodeNumber = -2.0,
        seenAt = Date(1697247357L),
        coverData = AnimeCover(
            animeId = 3L,
            sourceId = 4L,
            isAnimeFavorite = false,
            url = "https://example.com/cover.png",
            lastModified = 5L,
        ),
    )

    override val values: Sequence<AnimeHistoryWithRelations>
        get() = sequenceOf(simple, historyWithoutReadAt, historyWithNegativeChapterNumber)
}
