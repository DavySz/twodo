package com.studies.twodo.ui.features

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.studies.twodo.domain.Todo
import com.studies.twodo.domain.fakeTodoFinished
import com.studies.twodo.ui.theme.TwodoTheme
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.unit.dp
import com.studies.twodo.domain.fakeTodoUnfinished
import com.studies.twodo.ui.components.TodoItem

@Composable
fun ListScreen() {
    ListContent(todos = emptyList())
}

@Composable
fun ListContent(todos: List<Todo>) {
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        LazyColumn (
            modifier = Modifier
                .consumeWindowInsets(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(todos) { index, todo ->
                TodoItem(
                    todo = todo,
                    onClickItem = {},
                    onCheckItem = {},
                    onDeleteItem = {}
                )
                if(index < todos.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun ListContentPreview() {
    TwodoTheme {
        ListContent(todos = listOf(fakeTodoFinished, fakeTodoUnfinished, fakeTodoUnfinished))
    }
}