package tasks

import com.guan.coroutinecompare.network.service.GitHubService
import com.guan.coroutinecompare.network.service.RequestData
import com.guan.coroutinecompare.network.service.User
import com.guan.coroutinecompare.utils.bodyList
import contributors.logRepos
import contributors.logUsers

suspend fun loadContributorsSuspend(service: GitHubService, req: RequestData): List<User> {
    val repos = service
        .getOrgRepos(req.org)
        .also { logRepos(req, it) }
        .bodyList()

    return repos.flatMap { repo ->
        service
            .getRepoContributors(req.org, repo.name)
            .also { logUsers(repo, it) }
            .bodyList()
    }.aggregate()
}