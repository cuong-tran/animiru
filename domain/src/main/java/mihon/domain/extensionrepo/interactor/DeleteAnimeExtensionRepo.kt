package mihon.domain.extensionrepo.interactor

import mihon.domain.extensionrepo.repository.AnimeExtensionRepoRepository

class DeleteAnimeExtensionRepo(
    private val repository: AnimeExtensionRepoRepository,
) {
    suspend fun await(baseUrl: String) {
        repository.deleteRepo(baseUrl)
    }
}
