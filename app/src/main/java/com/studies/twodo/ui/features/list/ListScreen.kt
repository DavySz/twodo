package com.studies.twodo.ui.features.list

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.studies.twodo.data.TodoDatabaseProvider
import com.studies.twodo.data.TodoRepositoryImpl
import com.studies.twodo.domain.fakeTodoUnfinished
import com.studies.twodo.ui.components.TodoItem
import androidx.compose.runtime.getValue
import com.studies.twodo.navigation.AddEditRoute
import com.studies.twodo.ui.UiEvent

@Composable
fun ListScreen(
    navigateToAddEditScreen: (id: Long?) -> Unit
) {
    val context = LocalContext.current.applicationContext
    val database = TodoDatabaseProvider.provide(context)
    val repository = TodoRepositoryImpl(
        dao = database.todoDAO
    )

    val viewModel = viewModel<ListViewModel> {
        ListViewModel(repository = repository)
    }

    val todos by viewModel.todos.collectAsState()

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.goBack -> {}
                is UiEvent.showSnackbar -> {}
                is UiEvent.navigate<*> -> {
                    when(uiEvent.route){
                        is AddEditRoute -> {
                            navigateToAddEditScreen(uiEvent.route.id)
                        }
                    }
                }
            }
        }
    }

    ListContent(
        todos = todos,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ListContent(
    todos: List<Todo>,
    onEvent: (ListEvent) -> Unit
) {
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(ListEvent.addEdit(null)) }) {
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
                    onClickItem = {
                        onEvent(ListEvent.addEdit(todo.id))
                    },
                    onCheckItem = {
                        onEvent(ListEvent.onFinishTask(
                            todo.id,
                            it
                        ))
                    },
                    onDeleteItem = {
                        onEvent(ListEvent.delete(todo.id))
                    }
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
        ListContent(
            todos = listOf(fakeTodoFinished, fakeTodoUnfinished, fakeTodoUnfinished),
            onEvent = {}
        )
    }
}