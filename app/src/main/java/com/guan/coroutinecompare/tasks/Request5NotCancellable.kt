package tasks

import com.guan.coroutinecompare.network.service.GitHubService
import com.guan.coroutinecompare.network.service.RequestData
import com.guan.coroutinecompare.network.service.User
import com.guan.coroutinecompare.utils.bodyList
import contributors.logRepos
import contributors.logUsers
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

suspend fun loadContributorsNotCancellable(service: GitHubService, req: RequestData): List<User> {
    val repos = service
        .getOrgRepos(req.org)
        .also { logRepos(req, it) }
        .bodyList()

    val deferred: List<Deferred<List<User>>> = repos.map { repo ->
        GlobalScope.async {
            service.getRepoContributors(req.org, repo.name)
                .also { logUsers(repo, it) }
                .bodyList()
        }
    }
    return deferred.awaitAll().flatten().aggregate()
}
