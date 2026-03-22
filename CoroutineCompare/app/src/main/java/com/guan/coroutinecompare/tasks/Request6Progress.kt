package tasks

import com.guan.coroutinecompare.network.service.GitHubService
import com.guan.coroutinecompare.network.service.RequestData
import com.guan.coroutinecompare.network.service.User
import com.guan.coroutinecompare.utils.bodyList
import contributors.logRepos
import contributors.logUsers

suspend fun loadContributorsProgress(
    service: GitHubService,
    req: RequestData,
    updateResults: suspend (List<User>, String) -> Unit
) {
    val startTime = System.currentTimeMillis()
    val repos = service
        .getOrgRepos(req.org)
        .also { logRepos(req, it) }
        .bodyList()

    var allUsers = emptyList<User>()

    for ((index, repo) in repos.withIndex()) {
        val users = service
            .getRepoContributors(req.org, repo.name)
            .also { logUsers(repo, it) }
            .bodyList()

        allUsers = (allUsers + users).aggregate()
        val time = System.currentTimeMillis() - startTime
        updateResults(
            allUsers,
            "${if (index == repos.lastIndex) "Done" else "Not yet Done!"} Cost ${time / 1000} s"
        )
    }
}
