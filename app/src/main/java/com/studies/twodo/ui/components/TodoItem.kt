package com.studies.twodo.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.studies.twodo.domain.Todo
import com.studies.twodo.domain.fakeTodoFinished
import com.studies.twodo.domain.fakeTodoUnfinished
import com.studies.twodo.ui.theme.TwodoTheme

@Composable
fun TodoItem(
    todo: Todo,
    onClickItem: () -> Unit,
    onDeleteItem: () -> Unit,
    onCheckItem: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface (
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        onClick = onClickItem
    ) {
        Row (
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(checked = todo.isFinished, onCheckedChange = onCheckItem)
            Spacer(modifier = Modifier.width(8.dp))
            Column (
                modifier = Modifier.weight(1f)
            ) {
                Text(text = todo.title, style = MaterialTheme.typography.titleLarge)
                todo.description?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = todo.description, style = MaterialTheme.typography.bodyLarge)
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onDeleteItem) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Preview
@Composable
private fun TodoItemFinishedPreview() {
    TwodoTheme {
        TodoItem(
            todo = fakeTodoFinished,
            onDeleteItem = {},
            onCheckItem = {},
            onClickItem = {}
        )
    }
}

@Preview
@Composable
private fun TodoItemUnfinishedPreview() {
    TwodoTheme {
        TodoItem(
            todo = fakeTodoUnfinished,
            onDeleteItem = {},
            onCheckItem = {},
            onClickItem = {}
        )
    }
}
