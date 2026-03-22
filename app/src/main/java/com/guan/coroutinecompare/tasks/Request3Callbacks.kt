package tasks

import com.guan.coroutinecompare.network.service.GitHubService
import com.guan.coroutinecompare.network.service.RequestData
import com.guan.coroutinecompare.network.service.User
import com.guan.coroutinecompare.utils.bodyList
import contributors.log
import contributors.logRepos
import contributors.logUsers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//fun loadContributorsCallbacks(
//    service: GitHubService,
//    req: RequestData,
//    updateResults: (users: List<User>, respTint: String) -> Unit
//) {
//    service.getOrgReposCall(req.org).onResponse { responseRepos ->
//        logRepos(req, responseRepos)
//        val repos = responseRepos.bodyList()
//        val latch = CountDownLatch(repos.size)
//        val allUsers = mutableListOf<User>()
//
//        val time = measureTimeMillis {
//            for (repo in repos) {
//                service.getRepoContributorsCall(req.org, repo.name).onResponse { responseUsers ->
//                    logUsers(repo, responseUsers)
//                    val users = responseUsers.bodyList()
//                    synchronized(allUsers){
//                        allUsers += users
//                    }
//                    latch.countDown()
//                }
//            }
//            latch.await()
//        }
//
//        //: Why this code doesn't work? How to fix that?
//        // 异步调用，for循环结束之后allUsers可能还是空的，所以页面内不显示内容
//
//        updateResults.invoke(allUsers.aggregate(), "loadContributorsCallbacks cost $time")
//    }
//}

fun loadContributorsCallbacks(
    service: GitHubService,
    req: RequestData,
    updateResults: (users: List<User>, respTint: String) -> Unit
) {
    val startTime = System.currentTimeMillis()
    service.getOrgReposCall(req.org).onResponse { responseRepos ->
        logRepos(req, responseRepos)
        val repos = responseRepos.bodyList()
        var completed = 0
        val allUsers = mutableListOf<User>()
        for (repo in repos) {
            service.getRepoContributorsCall(req.org, repo.name).onResponse { responseUsers ->
                logUsers(repo, responseUsers)
                val users = responseUsers.bodyList()
                synchronized(allUsers) {
                    allUsers += users
                    completed += 1
                    val time = System.currentTimeMillis() - startTime
                    updateResults.invoke(
                        allUsers.aggregate(),
                        "${if (completed == repos.size) "Done" else "Not yet Done!"} Cost ${time / 1000} s"
                    )
                }
            }
        }

        //: Why this code doesn't work? How to fix that?
        // 异步调用，for循环结束之后allUsers可能还是空的，所以页面内不显示内容
    }
}

inline fun <T> Call<T>.onResponse(crossinline callback: (Response<T>) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            callback(response)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            log.error("Call failed", t)
        }
    })
}
