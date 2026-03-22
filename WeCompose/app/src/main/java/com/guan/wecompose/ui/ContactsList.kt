package com.guan.wecompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.guan.wecompose.R
import com.guan.wecompose.data.bean.Contact
import com.guan.wecompose.data.bean.ContactTool
import com.guan.wecompose.ui.theme.WeComposeTheme
import com.guan.wecompose.ui.viewModel.WeViewModel

@Composable
fun ContactsList(contacts: List<Contact>, contactTools: List<ContactTool>) {
    Column {
        ContactTopBar()
        Box(
            Modifier
                .background(WeComposeTheme.colors.background)
        ) {
            LazyColumn {
                itemsIndexed(contactTools) { index, tool ->
                    if (index > 0) {
                        HorizontalDivider(
                            Modifier.padding(start = 68.dp),
                            color = WeComposeTheme.colors.divider,
                            thickness = 0.8f.dp
                        )
                    }
                    ContactToolItem(tool)
                }
            }
        }

        Text(
            "朋友",
            style = MaterialTheme.typography.bodyMedium,
            color = WeComposeTheme.colors.textPrimary,
            modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 4.dp)
        )

        Box(
            Modifier
                .fillMaxSize()
                .background(WeComposeTheme.colors.background)
        ) {
            LazyColumn {
                itemsIndexed(contacts) { index, contact ->
                    if (index > 0) {
                        HorizontalDivider(
                            Modifier.padding(start = 68.dp),
                            color = WeComposeTheme.colors.divider,
                            thickness = 0.8f.dp
                        )
                    }
                    ContactItem(contact)
                }
            }
        }
    }
}

@Composable
fun ContactToolItem(tool: ContactTool) {
    Row(
        Modifier
            .background(WeComposeTheme.colors.listItem)
            .padding(4.dp)
    ) {
        Image(
            painter = painterResource(tool.avatar),
            contentDescription = tool.name,
            Modifier
                .padding(4.dp)
                .size(36.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Text(
            tool.name,
            style = MaterialTheme.typography.bodyLarge,
            color = WeComposeTheme.colors.textPrimary,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun ContactItem(contact: Contact) {
    Row(
        Modifier
            .background(WeComposeTheme.colors.listItem)
            .padding(4.dp)
    ) {
        Image(
            painter = painterResource(contact.avatar),
            contentDescription = contact.name,
            Modifier
                .padding(4.dp)
                .size(36.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Text(
            contact.name,
            style = MaterialTheme.typography.bodyLarge,
            color = WeComposeTheme.colors.textPrimary,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
fun PreviewContactItem() {
    ContactItem(Contact("Guan", R.drawable.avatar_diuwuxian))
}

@Preview
@Composable
fun PreviewContactToolItem() {
    ContactToolItem(ContactTool("新的朋友", R.drawable.ic_contact_add))
}

@Preview
@Composable
fun PreviewContactsList() {
    val viewmodel: WeViewModel = viewModel()
    ContactsList(
        viewmodel.contacts,
        viewmodel.contactTools
    )
}

@Composable
fun ContactTopBar() {
    WeTopBar(title = "通讯录")
}
