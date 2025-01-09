package mihon.domain.extensionrepo.interactor

import mihon.domain.extensionrepo.model.ExtensionRepo
import mihon.domain.extensionrepo.repository.AnimeExtensionRepoRepository

class ReplaceAnimeExtensionRepo(
    private val repository: AnimeExtensionRepoRepository,
) {
    suspend fun await(repo: ExtensionRepo) {
        repository.replaceRepo(repo)
    }
}
