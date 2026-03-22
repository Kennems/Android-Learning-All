package com.zhouguan.composelearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhouguan.composelearn.data.Message
import com.zhouguan.composelearn.todo.four.TodoScreen
import com.zhouguan.composelearn.todo.four.TodoViewModel
import com.zhouguan.composelearn.ui.theme.ComPoseLearnTheme
import kotlinx.coroutines.launch

// -------------------------- Main Activity --------------------------
class MainActivity : ComponentActivity() {
    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComPoseLearnTheme {
                MainScreen(todoViewModel)
            }
        }
    }

}

@Composable
fun MainScreen(todoViewModel: TodoViewModel) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Baseline", "List", "Layout", "ChatList", "Todo")

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        icon = {
                            when (index) {
                                0 -> Icon(Icons.Default.TextFields, contentDescription = title)
                                1 -> Icon(Icons.AutoMirrored.Filled.List, contentDescription = title)
                                2 -> Icon(Icons.Default.GridOn, contentDescription = title)
                                3 -> Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = title)
                                4 -> Icon(Icons.Default.CheckBox, contentDescription = title)
                                else -> Icon(Icons.AutoMirrored.Filled.Help, contentDescription = title)
                            }
                        },
                        label = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            when (selectedTab) {
                0 -> TextWithPaddingToBaselinePage()
                1 -> ScrollingListPage()
                2 -> LayoutStudyPage()
                3 -> ChatListPage()
                4 -> TodoActivityScreen(todoViewModel)
            }
        }
    }
}

@Composable
fun TextWithPaddingToBaselinePage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEBEE))
    ) {
        Text(
            text = "Hi there 👋",
            modifier = Modifier
                .firstBaselineToTop(32.dp)
                .background(Color.Red)
                .padding(8.dp),
            color = Color.White
        )
    }
}

@Preview
@Composable
fun PreviewTextWithPaddingToBaselinePage() {
    ComPoseLearnTheme {
        TextWithPaddingToBaselinePage()
    }
}

// -------------------------- Page 2: Scrolling List --------------------------
@Composable
fun ScrollingListPage() {
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Row {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { coroutineScope.launch { scrollState.animateScrollToItem(0) } }
            ) { Text(text = "Top") }

            Spacer(Modifier.width(8.dp))

            Button(
                modifier = Modifier.weight(1f),
                onClick = { coroutineScope.launch { scrollState.animateScrollToItem(listSize - 1) } }
            ) { Text(text = "End") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(state = scrollState) {
            items((0 until listSize).toList()) { index ->
                ImageListItem(index)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewScrollingListPage() {
    ComPoseLearnTheme {
        ScrollingListPage()
    }
}

// -------------------------- Page 3: Layout Study --------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutStudyPage() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Layout Study") },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Favorite, contentDescription = "Favorite")
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(Modifier.padding(innerPadding))
    }
}

@Preview
@Composable
fun PreviewLayoutStudyPage() {
    ComPoseLearnTheme {
        LayoutStudyPage()
    }
}

@Composable
fun ChatListPage() {
    ComPoseLearnTheme() {
        val sampleMessages = listOf(
            Message(
                "Alice",
                "Hey there! Just finished an amazing project at work and feeling really excited about the potential impact. Can't wait to share more details with you soon!"
            ),
            Message(
                "Bob",
                "I've been exploring some new hiking trails in the mountain region. The landscapes are absolutely breathtaking, and the natural beauty is just incredible. Thinking of planning a longer trek next month."
            ),
            Message(
                "Charlie",
                "Programming is such an interesting field! I've been diving deep into Kotlin and Jetpack Compose, and I'm constantly amazed by how elegant and powerful the language and framework are."
            ),
            Message(
                "David",
                "Tried out a new recipe for homemade pizza last night. Spent hours perfecting the dough and experimenting with different toppings. It turned out better than any restaurant pizza I've had!"
            ),
            Message(
                "Emma",
                "Just got back from an incredible conference about artificial intelligence and machine learning. The keynote speakers were mind-blowing, and I learned so many cutting-edge techniques."
            ),
            Message(
                "Frank",
                "Thinking about starting a side project that combines my passion for photography and mobile app development. Any suggestions on interesting app ideas that could showcase visual creativity?"
            ),
            Message(
                "Grace",
                "Reading an fascinating book about the history of technology innovation. It's fascinating how small ideas can transform entire industries and change the way we live and work."
            ),
            Message(
                "Henry",
                "Spent the weekend learning advanced Kotlin coroutines and concurrent programming. The complexity is challenging, but also incredibly rewarding when you start understanding the concepts."
            ),
            Message(
                "Isabella",
                "Planning a cross-country road trip and researching the best routes, attractions, and hidden gems along the way. Travel is such an amazing way to broaden one's perspective."
            ),
            Message(
                "Jack",
                "Just completed a marathon coding session developing a complex Android application using Jetpack Compose. The declarative UI approach is a game-changer!"
            )
        )

        ComPoseLearnTheme {
            Conversation(messages = sampleMessages)
        }
    }
}

@Preview
@Composable
fun PreviewChatListPage() {
    ChatListPage()
}

@Composable
private fun TodoActivityScreen(
    todoViewModel: TodoViewModel,
    modifier: Modifier = Modifier
) {
    TodoScreen(
        items = todoViewModel.todoItems,
        currentlyEditing = todoViewModel.currentEditItem,
        onAddItem = todoViewModel::addItem,
        onRemoveItem = todoViewModel::removeItem,
        onStartEdit = todoViewModel::onEditItemSelected,
        onEditItemChange = todoViewModel::onEditItemChange,
        onEditDone = todoViewModel::onEditDone,
        modifier = modifier
    )
}