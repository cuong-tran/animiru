package mihon.domain.extensionrepo.interactor

import mihon.domain.extensionrepo.repository.AnimeExtensionRepoRepository

class GetAnimeExtensionRepoCount(
    private val repository: AnimeExtensionRepoRepository,
) {
    fun subscribe() = repository.getCount()
}
