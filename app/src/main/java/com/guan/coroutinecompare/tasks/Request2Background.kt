package tasks

import com.guan.coroutinecompare.network.service.GitHubService
import com.guan.coroutinecompare.network.service.RequestData
import com.guan.coroutinecompare.network.service.User
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

fun loadContributorsBackground(
    service: GitHubService,
    req: RequestData,
    updateResults: (List<User>, String) -> Unit
) {
    thread {
        thread {
            var users: List<User> = emptyList()
            val time = measureTimeMillis {
                users = loadContributorsBlocking(service, req)
            }
            updateResults(users, "loadContributorsBackground cost ${time/1000} s")
        }
    }
}