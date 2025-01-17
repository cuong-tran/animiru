package eu.kanade.domain

import android.app.Application
import eu.kanade.domain.anime.interactor.SetAnimeViewerFlags
import eu.kanade.domain.anime.interactor.UpdateAnime
import eu.kanade.domain.download.interactor.DeleteDownload
import eu.kanade.domain.episode.interactor.SetSeenStatus
import eu.kanade.domain.episode.interactor.SyncEpisodesWithSource
import eu.kanade.domain.extension.interactor.GetExtensionLanguages
import eu.kanade.domain.extension.interactor.GetExtensionSources
import eu.kanade.domain.extension.interactor.GetExtensionsByType
import eu.kanade.domain.extension.interactor.TrustExtension
import eu.kanade.domain.source.interactor.GetSourcesWithFavoriteCount
import eu.kanade.domain.source.interactor.GetEnabledSources
import eu.kanade.domain.source.interactor.GetLanguagesWithSources
import eu.kanade.domain.source.interactor.SetMigrateSorting
import eu.kanade.domain.source.interactor.ToggleSource
import eu.kanade.domain.source.interactor.ToggleSourcePin
import eu.kanade.domain.source.interactor.ToggleLanguage
import eu.kanade.domain.track.interactor.AddTracks
import eu.kanade.domain.track.interactor.RefreshTracks
import eu.kanade.domain.track.interactor.SyncEpisodeProgressWithTrack
import eu.kanade.domain.track.interactor.TrackEpisode
import mihon.data.repository.AnimeExtensionRepoRepositoryImpl
import mihon.domain.episode.interactor.FilterEpisodesForDownload
import mihon.domain.extensionrepo.interactor.CreateAnimeExtensionRepo
import mihon.domain.extensionrepo.interactor.DeleteAnimeExtensionRepo
import mihon.domain.extensionrepo.interactor.GetAnimeExtensionRepo
import mihon.domain.extensionrepo.interactor.GetAnimeExtensionRepoCount
import mihon.domain.extensionrepo.interactor.ReplaceAnimeExtensionRepo
import mihon.domain.extensionrepo.interactor.UpdateAnimeExtensionRepo
import mihon.domain.extensionrepo.repository.AnimeExtensionRepoRepository
import mihon.domain.extensionrepo.service.ExtensionRepoService
import mihon.domain.upcoming.interactor.GetUpcomingAnime
import tachiyomi.data.anime.AnimeRepositoryImpl
import tachiyomi.data.anime.CustomAnimeRepositoryImpl
import tachiyomi.data.category.AnimeCategoryRepositoryImpl
import tachiyomi.data.episode.EpisodeRepositoryImpl
import tachiyomi.data.history.AnimeHistoryRepositoryImpl
import tachiyomi.data.release.ReleaseServiceImpl
import tachiyomi.data.source.AnimeSourceRepositoryImpl
import tachiyomi.data.source.AnimeStubSourceRepositoryImpl
import tachiyomi.data.track.AnimeTrackRepositoryImpl
import tachiyomi.data.updates.AnimeUpdatesRepositoryImpl
import tachiyomi.domain.anime.interactor.AnimeFetchInterval
import tachiyomi.domain.anime.interactor.GetAnime
import tachiyomi.domain.anime.interactor.GetAnimeByUrlAndSourceId
import tachiyomi.domain.anime.interactor.GetAnimeFavorites
import tachiyomi.domain.anime.interactor.GetAnimeWithEpisodes
import tachiyomi.domain.anime.interactor.GetCustomAnimeInfo
import tachiyomi.domain.anime.interactor.GetDuplicateLibraryAnime
import tachiyomi.domain.anime.interactor.GetLibraryAnime
import tachiyomi.domain.anime.interactor.NetworkToLocalAnime
import tachiyomi.domain.anime.interactor.ResetAnimeViewerFlags
import tachiyomi.domain.anime.interactor.SetAnimeEpisodeFlags
import tachiyomi.domain.anime.interactor.SetCustomAnimeInfo
import tachiyomi.domain.anime.repository.AnimeRepository
import tachiyomi.domain.anime.repository.CustomAnimeRepository
import tachiyomi.domain.category.interactor.CreateAnimeCategoryWithName
import tachiyomi.domain.category.interactor.DeleteAnimeCategory
import tachiyomi.domain.category.interactor.GetAnimeCategories
import tachiyomi.domain.category.interactor.GetVisibleAnimeCategories
import tachiyomi.domain.category.interactor.HideAnimeCategory
import tachiyomi.domain.category.interactor.RenameAnimeCategory
import tachiyomi.domain.category.interactor.ReorderAnimeCategory
import tachiyomi.domain.category.interactor.ResetAnimeCategoryFlags
import tachiyomi.domain.category.interactor.SetAnimeCategories
import tachiyomi.domain.category.interactor.SetAnimeDisplayMode
import tachiyomi.domain.category.interactor.SetSortModeForAnimeCategory
import tachiyomi.domain.category.interactor.UpdateAnimeCategory
import tachiyomi.domain.category.repository.AnimeCategoryRepository
import tachiyomi.domain.episode.interactor.GetEpisode
import tachiyomi.domain.episode.interactor.GetEpisodeByUrlAndAnimeId
import tachiyomi.domain.episode.interactor.GetEpisodesByAnimeId
import tachiyomi.domain.episode.interactor.SetAnimeDefaultEpisodeFlags
import tachiyomi.domain.episode.interactor.ShouldUpdateDbEpisode
import tachiyomi.domain.episode.interactor.UpdateEpisode
import tachiyomi.domain.episode.repository.EpisodeRepository
import tachiyomi.domain.history.interactor.GetAnimeHistory
import tachiyomi.domain.history.interactor.GetNextEpisodes
import tachiyomi.domain.history.interactor.RemoveAnimeHistory
import tachiyomi.domain.history.interactor.UpsertAnimeHistory
import tachiyomi.domain.history.repository.AnimeHistoryRepository
import tachiyomi.domain.release.interactor.GetApplicationRelease
import tachiyomi.domain.release.service.ReleaseService
import tachiyomi.domain.source.interactor.GetAnimeSourcesWithNonLibraryAnime
import tachiyomi.domain.source.interactor.GetRemoteAnime
import tachiyomi.domain.source.repository.AnimeSourceRepository
import tachiyomi.domain.source.repository.AnimeStubSourceRepository
import tachiyomi.domain.track.interactor.DeleteAnimeTrack
import tachiyomi.domain.track.interactor.GetAnimeTracks
import tachiyomi.domain.track.interactor.GetTracksPerAnime
import tachiyomi.domain.track.interactor.InsertAnimeTrack
import tachiyomi.domain.track.repository.AnimeTrackRepository
import tachiyomi.domain.updates.interactor.GetAnimeUpdates
import tachiyomi.domain.updates.repository.AnimeUpdatesRepository
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.addFactory
import uy.kohesive.injekt.api.addSingletonFactory
import uy.kohesive.injekt.api.get

class DomainModule : InjektModule {

    override fun InjektRegistrar.registerInjectables() {
        addSingletonFactory<AnimeCategoryRepository> { AnimeCategoryRepositoryImpl(get()) }
        addFactory { GetAnimeCategories(get()) }
        addFactory { GetVisibleAnimeCategories(get()) }
        addFactory { ResetAnimeCategoryFlags(get(), get()) }
        addFactory { SetAnimeDisplayMode(get()) }
        addFactory { SetSortModeForAnimeCategory(get(), get()) }
        addFactory { CreateAnimeCategoryWithName(get(), get()) }
        addFactory { RenameAnimeCategory(get()) }
        addFactory { ReorderAnimeCategory(get()) }
        addFactory { UpdateAnimeCategory(get()) }
        addFactory { HideAnimeCategory(get()) }
        addFactory { DeleteAnimeCategory(get()) }

        addSingletonFactory<AnimeRepository> { AnimeRepositoryImpl(get()) }
        addFactory { GetDuplicateLibraryAnime(get()) }
        addFactory { GetAnimeFavorites(get()) }
        addFactory { GetLibraryAnime(get()) }
        addFactory { GetAnimeWithEpisodes(get(), get()) }
        addFactory { GetAnimeByUrlAndSourceId(get()) }
        addFactory { GetAnime(get()) }
        addFactory { GetNextEpisodes(get(), get(), get()) }
        addFactory { GetUpcomingAnime(get()) }
        addFactory { ResetAnimeViewerFlags(get()) }
        addFactory { SetAnimeEpisodeFlags(get()) }
        addFactory { AnimeFetchInterval(get()) }
        addFactory { SetAnimeDefaultEpisodeFlags(get(), get(), get()) }
        addFactory { SetAnimeViewerFlags(get()) }
        addFactory { NetworkToLocalAnime(get()) }
        addFactory { UpdateAnime(get(), get()) }
        addFactory { SetAnimeCategories(get()) }

        addSingletonFactory<ReleaseService> { ReleaseServiceImpl(get(), get()) }
        addFactory { GetApplicationRelease(get(), get()) }

        addSingletonFactory<AnimeTrackRepository> { AnimeTrackRepositoryImpl(get()) }
        addFactory { TrackEpisode(get(), get(), get(), get()) }
        addFactory { AddTracks(get(), get(), get(), get()) }
        addFactory { RefreshTracks(get(), get(), get(), get()) }
        addFactory { DeleteAnimeTrack(get()) }
        addFactory { GetTracksPerAnime(get()) }
        addFactory { GetAnimeTracks(get()) }
        addFactory { InsertAnimeTrack(get()) }
        addFactory { SyncEpisodeProgressWithTrack(get(), get(), get()) }

        addSingletonFactory<EpisodeRepository> { EpisodeRepositoryImpl(get()) }
        addFactory { GetEpisode(get()) }
        addFactory { GetEpisodesByAnimeId(get()) }
        addFactory { GetEpisodeByUrlAndAnimeId(get()) }
        addFactory { UpdateEpisode(get()) }
        addFactory { SetSeenStatus(get(), get(), get(), get()) }
        addFactory { ShouldUpdateDbEpisode() }
        addFactory { SyncEpisodesWithSource(get(), get(), get(), get(), get(), get(), get()) }
        addFactory { FilterEpisodesForDownload(get(), get(), get()) }

        addSingletonFactory<AnimeHistoryRepository> { AnimeHistoryRepositoryImpl(get()) }
        addFactory { GetAnimeHistory(get()) }
        addFactory { UpsertAnimeHistory(get()) }
        addFactory { RemoveAnimeHistory(get()) }

        addFactory { DeleteDownload(get(), get()) }

        addFactory { GetExtensionsByType(get(), get()) }
        addFactory { GetExtensionSources(get()) }
        addFactory { GetExtensionLanguages(get(), get()) }

        addSingletonFactory<AnimeUpdatesRepository> { AnimeUpdatesRepositoryImpl(get()) }
        addFactory { GetAnimeUpdates(get()) }

        addSingletonFactory<AnimeSourceRepository> { AnimeSourceRepositoryImpl(get(), get()) }
        addSingletonFactory<AnimeStubSourceRepository> { AnimeStubSourceRepositoryImpl(get()) }
        addFactory { GetEnabledSources(get(), get()) }
        addFactory { GetLanguagesWithSources(get(), get()) }
        addFactory { GetRemoteAnime(get()) }
        addFactory { GetSourcesWithFavoriteCount(get(), get()) }
        addFactory { GetAnimeSourcesWithNonLibraryAnime(get()) }
        addFactory { ToggleSource(get()) }
        addFactory { ToggleSourcePin(get()) }

        addFactory { SetMigrateSorting(get()) }
        addFactory { ToggleLanguage(get()) }
        addFactory { TrustExtension(get(), get()) }

        addFactory { ExtensionRepoService(get(), get()) }

        addSingletonFactory<AnimeExtensionRepoRepository> { AnimeExtensionRepoRepositoryImpl(get()) }
        addFactory { GetAnimeExtensionRepo(get()) }
        addFactory { GetAnimeExtensionRepoCount(get()) }
        addFactory { CreateAnimeExtensionRepo(get(), get()) }
        addFactory { DeleteAnimeExtensionRepo(get()) }
        addFactory { ReplaceAnimeExtensionRepo(get()) }
        addFactory { UpdateAnimeExtensionRepo(get(), get()) }

        // AM (CUSTOM_INFORMATION) -->
        addSingletonFactory<CustomAnimeRepository> { CustomAnimeRepositoryImpl(get<Application>()) }
        addFactory { GetCustomAnimeInfo(get()) }
        addFactory { SetCustomAnimeInfo(get()) }
        // <-- AM (CUSTOM_INFORMATION)
    }
}
