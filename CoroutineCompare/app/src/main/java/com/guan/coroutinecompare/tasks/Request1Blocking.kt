package tasks

import com.guan.coroutinecompare.network.service.GitHubService
import com.guan.coroutinecompare.network.service.RequestData
import com.guan.coroutinecompare.network.service.User
import com.guan.coroutinecompare.utils.bodyList
import contributors.*
import retrofit2.Response

fun loadContributorsBlocking(service: GitHubService, req: RequestData) : List<User> {
    val repos = service
        .getOrgReposCall(req.org)
        .execute() // Executes request and blocks the current thread
        .also { logRepos(req, it) }
        .body() ?: emptyList()

    return repos.flatMap { repo ->
        service
            .getRepoContributorsCall(req.org, repo.name)
            .execute() // Executes request and blocks the current thread
            .also { logUsers(repo, it) }
            .bodyList()
    }.aggregate()
}
