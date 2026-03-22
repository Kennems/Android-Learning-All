package com.zhouguan.composelearn.todo.four

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zhouguan.composelearn.todo.TodoIcon
import com.zhouguan.composelearn.todo.TodoItem
import com.zhouguan.composelearn.todo.one.AnimatedIconRow
import com.zhouguan.composelearn.todo.one.IconRow
import com.zhouguan.composelearn.todo.one.SelectableIconButton
import com.zhouguan.composelearn.todo.one.TodoEditButton


private const val TAG = "TodoComponents"

@Composable
fun TodoInputText(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onImeAction: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        onValueChange = onTextChange,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
                keyboardController?.hide()
            }
        )
    )
}

@Composable
fun TodoEditButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    TextButton(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(),
        modifier = modifier,
        enabled = enabled
    ) {
        Text(text = text)
    }
}

@Composable
fun AnimatedIconRow(
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true
) {
    val enter = remember { fadeIn(animationSpec = TweenSpec(300, easing = FastOutLinearInEasing)) }
    val exit = remember { fadeOut(animationSpec = TweenSpec(100, easing = FastOutSlowInEasing)) }
    Box(
        modifier.defaultMinSize(minHeight = 16.dp)
    )
    {
        AnimatedVisibility(
            visible = visible,
            enter = enter,
            exit = exit
        ) {
            IconRow(icon = icon, onIconChange = onIconChange, modifier = modifier)
        }
    }
}

@Composable
fun IconRow(
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        for (todoIcon in TodoIcon.entries) {
            SelectableIconButton(
                icon = todoIcon.imageVector,
                iconContentDescription = stringResource(id = todoIcon.contentDescription),
                onIconSelected = {
                    onIconChange(todoIcon)
                    Log.d(TAG, "IconRow: ${todoIcon.contentDescription}")
                },
                isSelected = (icon == todoIcon),
                modifier
            )
        }
    }
}

@Composable
fun SelectableIconButton(
    icon: ImageVector,
    iconContentDescription: String,
    onIconSelected: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val tint = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    }

    TextButton(
        onClick = onIconSelected,
        shape = CircleShape,
        modifier = modifier,
    ) {
        Column {
            Icon(
                imageVector = icon,
                contentDescription = iconContentDescription,
                tint = tint
            )
            if (isSelected) {
                Box(
                    Modifier
                        .padding(top = 3.dp)
                        .width(icon.defaultWidth)
                        .height(1.dp)
                        .background(tint)

                )
            } else {
                Spacer(
                    modifier = Modifier
                        .height(4.dp)
                )
            }
        }
    }
}

@Composable
fun TodoItemEntryInput(onItemComplete: (TodoItem) -> Unit) {
    val (text, setText) = remember { mutableStateOf("") }
    val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default) }

    val submit = {
        onItemComplete(
            TodoItem(
                task = text,
                icon = icon
            )
        )
        setIcon(TodoIcon.Default)
        setText("")
    }
    val iconVisible = text.isNotBlank()
    TodoItemInput(
        text = text,
        onTextChange = setText,
        icon = icon,
        onIconChange = setIcon,
        submit = submit,
        iconVisible = iconVisible,
    ){
        TodoEditButton(
            onClick = submit,
            text = "Add",
            enabled = text.isNotBlank()
        )
    }
}

@Composable
fun TodoItemInput(
    onIconChange: (TodoIcon) -> Unit,
    icon: TodoIcon,
    iconVisible: Boolean,
    onTextChange: (String) -> Unit,
    text: String,
    submit: () -> Unit,
    buttonSlot: @Composable () -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            TodoInputText(
                text = text,
                onTextChange = onTextChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                onImeAction = submit
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                buttonSlot()
            }
        }
        if (iconVisible) {
            AnimatedIconRow(
                icon = icon,
                onIconChange = onIconChange,
                modifier = Modifier.padding(top = 8.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TodoItemInputBackground(
    elevate: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    val animatedElevation by animateDpAsState(
        if (elevate) 1.dp else 0.dp,
        TweenSpec(300, easing = FastOutLinearInEasing)
    )
    Surface(
        color = MaterialTheme.colorScheme.onPrimary,
        shadowElevation = animatedElevation,
        shape = RectangleShape,
    ) {
        Row(
            modifier = modifier.animateContentSize(animationSpec = TweenSpec(300)),
            content = content
        )
    }
}

@Composable
fun TodoItemInlineEditor(
    item: TodoItem,
    onEditItemChange: (TodoItem) -> Unit,
    onEditDone: () -> Unit,
    onRemoveItem: () -> Unit
) {
    TodoItemInput(
        text = item.task,
        onTextChange = {
            onEditItemChange(item.copy(task = it))
        },
        icon = item.icon,
        onIconChange = {
            onEditItemChange(item.copy(icon = it))
        },
        iconVisible = true,
        submit = onEditDone,
    ){
        Row {
            val shrinkButtons = Modifier.widthIn(20.dp)
            TextButton(onClick = onEditDone, modifier = shrinkButtons) {
                Text(
                    text = "\uD83D\uDCBE",
                    textAlign = TextAlign.End
                )

            }

            TextButton(onClick = onRemoveItem, modifier = shrinkButtons) {
                Text(
                    text = "\uD83D\uDDD1\uFE0F",
                    textAlign = TextAlign.End
                )

            }
        }
    }
}