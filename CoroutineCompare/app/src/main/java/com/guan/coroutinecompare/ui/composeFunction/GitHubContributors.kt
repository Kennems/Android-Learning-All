package com.guan.coroutinecompare.ui.composeFunction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guan.coroutinecompare.network.service.GitHubService
import com.guan.coroutinecompare.network.service.RequestData
import com.guan.coroutinecompare.network.service.User
import com.guan.coroutinecompare.network.service.createGitHubService
import com.guan.coroutinecompare.samples.log
import com.guan.coroutinecompare.tasks.Variants
import com.guan.wecompose.ui.theme.GithubContributorsComposeTheme
import com.guan.wecompose.ui.theme.GithubContributorsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tasks.loadContributorsBackground
import tasks.loadContributorsCallbacks
import tasks.loadContributorsChannels
import tasks.loadContributorsConcurrent
import tasks.loadContributorsNotCancellable
import tasks.loadContributorsProgress
import tasks.loadContributorsSuspend
import kotlin.system.measureTimeMillis


@Composable
fun MainScreen() {
    GithubContributorsTheme(GithubContributorsComposeTheme.Theme.Light) {
        ContributorApp()
    }
}

private fun updateResults(
    f: ((service: GitHubService, req: RequestData) -> List<User>),
    service: GitHubService,
    req: RequestData,
    updateUI: ((List<User>, String) -> Unit)
): Unit {
    var users: List<User> = emptyList()
    val time = measureTimeMillis {
        users = f(service, req)
    }
    updateUI(users, "updateResults cost $time")

//    val start = System.nanoTime()
//    val users = f(service, req)
//    val time = (System.nanoTime() - start) / 1_000_000 // 转为毫秒
//    updateUI(users, time)
}

private fun updateResultsWithCallback(
    f: (GitHubService, RequestData, (List<User>, String) -> Unit) -> Unit,
    service: GitHubService,
    req: RequestData,
    updateUI: (List<User>, String) -> Unit
) {
    f.invoke(service, req, updateUI)
}

private suspend fun updateResultsWithCallbackSuspend(
    f: suspend (GitHubService, RequestData, suspend (List<User>, String) -> Unit) -> Unit,
    service: GitHubService,
    req: RequestData,
    updateUISuspend: suspend (List<User>, String) -> Unit
) {
    f.invoke(service, req, updateUISuspend)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContributorApp() {
    var username by remember { mutableStateOf("kennems") }
    var token by remember { mutableStateOf(BuildConfig.GITHUB_TOKEN) }
    var organization by remember { mutableStateOf("kotlin") }

    var selectedVariant by remember { mutableStateOf<Variants>(Variants.BLOCKING) }
    val variants = Variants.entries

    var contributors by remember { mutableStateOf(listOf<User>()) }
    var loadingStatus by remember { mutableStateOf("Start new loading") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(8.dp, top = 4.dp, end = 8.dp, bottom = 16.dp)
            .navigationBarsPadding()
    ) {
        CredentialsSection(
            username,
            token,
            onUsernameChange = { username = it },
            onTokenChange = { token = it })
        Spacer(Modifier.height(12.dp))

        OrganizationSection(organization, onOrgChange = { organization = it })
        ContributorsSpacer()
        var selectedVarianceIndex by remember { mutableStateOf(0) }
        LargeDropdownMenu(
            label = "Variant",
            items = variants,
            selectedIndex = selectedVarianceIndex,
            onItemSelected = { index, _ ->
                selectedVarianceIndex = index
                selectedVariant = variants[index]
            },
        )

        Spacer(Modifier.height(12.dp))
        ActionButtons(
            onLoadClick = {
//                loadingStatus = "Loading..."
//                // 模拟加载逻辑
//                contributors = listOf("JetBrains" to 200, "Google" to 150, "Square" to 80)
//                loadingStatus = "Loading status: completed in 1.6 sec"
                val updateUI: (users: List<User>, respTint: String) -> Unit = { users, respTint ->
                    log(users)
                    contributors = users
//                    loadingStatus = "Loading status: completed in $time sec"
                    loadingStatus = respTint
                }
                val updateUISuspend: suspend (List<User>, String) -> Unit = { users, respTint ->
                    log(users)
                    contributors = users
                    loadingStatus = respTint
                }
                loadingStatus = "网络请求中。。。"
                LoadContribuators(
                    username,
                    token,
                    organization,
                    updateUI,
                    updateUISuspend,
                    selectedVariant,
                    scope
                )
            },
            onCancelClick = {
                loadingStatus = "Cancelled"
            }
        )

        Spacer(Modifier.height(16.dp))
        Box(
            Modifier.weight(1f)
        ) {
            ContributorTable(contributors)
        }
        Spacer(Modifier.height(16.dp))
        StatusBar(loadingStatus)
    }
}

fun LoadContribuators(
    username: String,
    token: String,
    organization: String,
    updateUI: (List<User>, String) -> Unit,
    updateUISuspend: suspend (List<User>, String) -> Unit,
    selectedVariant: Variants,
    scope: CoroutineScope
) {
    val req = RequestData(username, token, organization)
    val service = createGitHubService(username, token)

    when (selectedVariant) {
        Variants.BLOCKING -> {
            // android 禁止在主线程用网络请求
//                        updateResults(
//                            ::loadContributorsBlocking,
//                            service,
//                            req,
//                            updateUI
//                        )
            updateUI(listOf(User("android 禁止在主线程用网络请求", 0)), "0")
        }

        Variants.BACKGROUND -> {
            updateResultsWithCallback(
                ::loadContributorsBackground,
                service,
                req,
                updateUI
            )
        }

        Variants.CALLBACKS -> {
            updateResultsWithCallback(
                ::loadContributorsCallbacks,
                service,
                req,
                updateUI
            )
        }

        Variants.SUSPEND -> {
//            LaunchedEffect(req.org) { // key 可以是 req.org 或其他唯一标识
//                var users: List<User> = emptyList()
//                val time = measureTimeMillis {
//                    users = loadContributorsSuspend(service, req)
//                }
//                updateUI(users, "Suspend Cost $time")
//            }
            scope.launch {
                var users: List<User> = emptyList()
                val time = measureTimeMillis {
                    users = loadContributorsSuspend(service, req)
                }
                updateUI(users, "Suspend Cost ${time / 1000} s")
            }
        }

        Variants.CONCURRENT -> {
            scope.launch {
                var users: List<User> = emptyList()
                val time = measureTimeMillis {
                    users = loadContributorsConcurrent(service, req)
                }
                updateUI(users, "Suspend Cost ${time / 1000} s")
            }
        }

        Variants.NOT_CANCELLABLE -> {
            scope.launch {
                var users: List<User> = emptyList()
                val time = measureTimeMillis {
                    users = loadContributorsNotCancellable(service, req)
                }
                updateUI(users, "Suspend Cost ${time / 1000} s")
            }
        }

        Variants.PROGRESS -> {
            scope.launch {
                updateResultsWithCallbackSuspend(
                    ::loadContributorsProgress,
                    service,
                    req,
                    updateUISuspend
                )
            }
        }

        Variants.CHANNELS -> {
            scope.launch {
                updateResultsWithCallbackSuspend(
                    ::loadContributorsChannels,
                    service,
                    req,
                    updateUISuspend
                )
            }
        }
    }
}

@Preview
@Composable
fun ContributorAppPreview() {
    ContributorApp()
}

@Composable
fun CredentialsSection(
    username: String,
    token: String,
    onUsernameChange: (String) -> Unit,
    onTokenChange: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = { Text("GitHub Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = token,
            onValueChange = onTokenChange,
            label = { Text("Password/Token") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun CredentialsSectionPreview() {
    CredentialsSection(
        username = "testuser",
        token = "123456",
        onUsernameChange = {},
        onTokenChange = {})
}

@Composable
fun OrganizationSection(
    organization: String,
    onOrgChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = organization,
        onValueChange = onOrgChange,
        label = { Text("Organization") },
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VariantSection(
    variant: String,
    variants: List<String>,
    onVariantChange: (String) -> Unit
) {
    Column {

        var expanded by rememberSaveable { mutableStateOf(false) }

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = variant,
                onValueChange = {
                },
                readOnly = true,
                label = { Text("Variant") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                variants.forEach { variantItem ->
                    key(variantItem) {
                        DropdownMenuItem(
                            text = { Text(variantItem) },
                            onClick = {
                                onVariantChange(variantItem)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VariantSectionPreview() {
    val variants = listOf(
        "BLOCKING", "BACKGROUND", "CALLBACKS",
        "SUSPEND", "CONCURRENT", "NOT_CANCELLABLE",
        "PROGRESS", "CHANNELS"
    )
    var selectedIndex by remember { mutableStateOf(-1) }
    LargeDropdownMenu(
        label = "Sample",
        items = variants,
        selectedIndex = selectedIndex,
        onItemSelected = { index, _ -> selectedIndex = index },
    )
//    VariantSection(
//        organization = "kotlin",
//        variant = "BLOCKING",
//        variants = variants,
//        onOrgChange = {},
//        onVariantChange = {})
}

@Composable
fun ActionButtons(onLoadClick: () -> Unit, onCancelClick: () -> Unit) {
    Row {
        Button(onClick = onLoadClick, modifier = Modifier.weight(1f)) {
            Text("Load contributors")
        }
        Spacer(Modifier.width(8.dp))
        OutlinedButton(onClick = onCancelClick, modifier = Modifier.weight(1f)) {
            Text("Cancel")
        }
    }
}

@Preview
@Composable
fun ActionButtonsPreview() {
    ActionButtons(onLoadClick = {}, onCancelClick = {})
}

@Composable
fun ContributorTable(contributors: List<User>) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(vertical = 8.dp)
        ) {
            Text(
                "Login", Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
            Text("Contributions", Modifier.weight(1f))
        }

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            itemsIndexed(contributors) { index, (login, contributions) ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        login, Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(contributions.toString(), Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview
@Composable
fun ContributorTablePreview() {
    val contributors = listOf(
        User("JakeWharton", 100),
        User("romainguy", 50),
        User("cbeyls", 25),
        User("JakeWharton", 100),
        User("romainguy", 50),
        User("cbeyls", 25),
        User("JakeWharton", 100),
        User("romainguy", 50),
        User("cbeyls", 25),
        User("JakeWharton", 100),
        User("romainguy", 50),
        User("cbeyls", 25),
        User("JakeWharton", 100),
        User("romainguy", 50),
        User("cbeyls", 25),
        User("JakeWharton", 100),
        User("romainguy", 50),
        User("cbeyls", 25), User("JakeWharton", 100),
        User("romainguy", 50),
        User("cbeyls", 25),
        User("JakeWharton", 100),
        User("romainguy", 50),
        User("cbeyls", 25),
        User("JakeWharton", 100),
        User("romainguy", 50),
        User("cbeyls", 25),
        User("JakeWharton", 100),
        User("romainguy", 50),
        User("cbeyls", 25),
        User("JakeWharton", 100),
        User("romainguy", 50),
        User("cbeyls", 25),
    )
    Column(
    ) {
        Box(
            Modifier.weight(1f)
        ) {
            ContributorTable(contributors)
        }
        StatusBar("Cost 10s")
    }
}

@Composable
fun StatusBar(status: String) {
    Text(
        text = status,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
    )
}

@Preview
@Composable
fun StatusBarPreview() {
    StatusBar(status = "Loading...")
}


@Composable
fun ContributorsSpacer() {
    Spacer(Modifier.height(8.dp))
}