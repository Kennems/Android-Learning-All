package com.zhouguan.composelearn

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhouguan.composelearn.data.Message
import com.zhouguan.composelearn.ui.theme.ComPoseLearnTheme


@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages.size) { messageIndex ->
            MessageCard(messages[messageIndex])
        }
    }
}

@Composable
fun MessageCard(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically, // Row 内部垂直居中
//            horizontalArrangement = Arrangement.Center // Row 内部水平居中
    ) {
        Image(
            painter = painterResource(id = R.drawable.boat),
            contentDescription = "boat",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop // 确保图片填充整个圆形区域
        )

        Spacer(modifier = Modifier.width(8.dp))
        var isExpanded by remember { mutableStateOf(false) }

        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        )

        Column(
            modifier = Modifier.clickable { isExpanded = !isExpanded }, // 图片和文字间添加间距
            verticalArrangement = Arrangement.Center, // Column 内部垂直居中
            horizontalAlignment = Alignment.Start // Column 内文字水平对齐方式
        ) {
            Text(
                text = "Hello ${message.author}!",
                color = MaterialTheme.colorScheme.onBackground
            )// 使用主题提供的颜色
            Spacer(modifier = Modifier.height(4.dp));
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = message.body,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                )// 使用主题提供的颜色
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConversation() {
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
