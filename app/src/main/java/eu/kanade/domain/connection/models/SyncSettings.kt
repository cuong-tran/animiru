// AM (SYNC) -->
package eu.kanade.domain.connection.models

data class SyncSettings(
    val animelibEntries: Boolean = true,
    val animeCategories: Boolean = true,
    val episodes: Boolean = true,
    val animeTracking: Boolean = true,
    val animeHistory: Boolean = true,
    val appSettings: Boolean = true,
    val sourceSettings: Boolean = true,
    val privateSettings: Boolean = false,
    // SY -->
    val customInfo: Boolean = true,
    // SY <--
)
// <-- AM (SYNC)
