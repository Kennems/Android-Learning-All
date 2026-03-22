package com.guan.wecompose.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.guan.wecompose.R
import com.guan.wecompose.data.bean.Chat
import com.guan.wecompose.data.bean.Contact
import com.guan.wecompose.data.bean.ContactTool
import com.guan.wecompose.data.bean.Msg
import com.guan.wecompose.data.bean.NavItem
import com.guan.wecompose.data.bean.User
import com.guan.wecompose.ui.theme.WeComposeTheme

class WeViewModel : ViewModel() {
    var selectedTab by mutableStateOf(0)
        private set

    var theme by mutableStateOf(value = WeComposeTheme.Theme.Light)

    var navTabs  = listOf(
        NavItem(R.drawable.ic_chat_filled, R.drawable.ic_chat_outlined, "Chat"),
        NavItem(R.drawable.ic_contacts_filled, R.drawable.ic_contacts_outlined, "Contacts"),
        NavItem(R.drawable.ic_discovery_filled, R.drawable.ic_discovery_outlined, "Discovery"),
        NavItem(R.drawable.ic_me_filled, R.drawable.ic_me_outlined, "Me")
    )

    var chats by mutableStateOf(
        listOf(
            Chat(
                friend = User("gaolaoshi", "高老师", R.drawable.avatar_gaolaoshi),
                mutableStateListOf(
                    Msg(
                        User("gaolaoshi", "高老师", R.drawable.avatar_gaolaoshi),
                        "锄禾日当午",
                        "14:20"
                    ),
                    Msg(User.Me, "汗滴禾下土", "14:20"),
                    Msg(User("gaolaoshi", "高老师", R.drawable.avatar_gaolaoshi), "谁知盘中餐", "14:20"),
                    Msg(User.Me, "粒粒皆辛苦", "14:20"),
                    Msg(User("gaolaoshi", "高老师", R.drawable.avatar_gaolaoshi), "唧唧复唧唧，木兰当户织。不闻机杼声，惟闻女叹息。", "14:20"),
                    Msg(User.Me, "双兔傍地走，安能辨我是雄雌？", "14:20"),
                    Msg(User("gaolaoshi", "高老师", R.drawable.avatar_gaolaoshi), "床前明月光，疑是地上霜。", "14:20"),
                    Msg(User.Me, "吃饭吧？", "14:20"),
                )
            ),
            Chat(
                friend = User("diuwuxian", "丢物线", R.drawable.avatar_diuwuxian),
                mutableStateListOf(
                    Msg(
                        User("diuwuxian", "丢物线", R.drawable.avatar_diuwuxian),
                        "哈哈哈",
                        "13:48"
                    ),
                    Msg(User.Me, "哈哈昂", "13:48"),
                    Msg(
                        User("diuwuxian", "丢物线", R.drawable.avatar_diuwuxian),
                        "你笑个屁呀",
                        "13:48"
                    ).apply { read = false },
                )
            ),
        )
    )

    var contacts by mutableStateOf(
        listOf(
            Contact("高老师", R.drawable.avatar_gaolaoshi),
            Contact("丢物线", R.drawable.avatar_diuwuxian)
        )
    )

    var contactTools by mutableStateOf(
        listOf(
            ContactTool("新的朋友", R.drawable.ic_contact_add),
            ContactTool("仅聊天", R.drawable.ic_contact_chat),
            ContactTool("群聊", R.drawable.ic_contact_group),
            ContactTool("标签", R.drawable.ic_contact_tag),
            ContactTool("公众号", R.drawable.ic_contact_official),
        )
    )


    fun switchTheme(){
        theme = when(theme){
            WeComposeTheme.Theme.Light -> WeComposeTheme.Theme.Dark
            WeComposeTheme.Theme.Dark -> WeComposeTheme.Theme.NewYear
            WeComposeTheme.Theme.NewYear -> WeComposeTheme.Theme.Light
        }
    }

    fun updateSelectedTab(index:Int): Unit{
        selectedTab = index
    }
}