package com.studies.twodo.ui.features.addedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.studies.twodo.data.TodoDatabaseProvider
import com.studies.twodo.data.TodoRepositoryImpl
import com.studies.twodo.ui.UiEvent
import com.studies.twodo.ui.theme.TwodoTheme

@Composable
fun AddEditScreen(
    goBack: () -> Unit
) {
    val context  = LocalContext.current.applicationContext
    val database = TodoDatabaseProvider.provide(context).todoDAO
    val repository = TodoRepositoryImpl(database)

    val viewModel = viewModel<AddEditViewModel> {
        AddEditViewModel(repository)
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.goBack -> {
                    goBack()
                }
                is UiEvent.showSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = uiEvent.message
                    )
                }
                is UiEvent.navigate<*> -> {}
            }
        }
    }

    AddEditScreenContent(
        title = viewModel.title,
        description = viewModel.description,
        onEvent = viewModel::onEvent,
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun AddEditScreenContent(
    title: String,
    description: String?,
    onEvent: (AddEditEvent) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(
                    AddEditEvent.save
                )
            }) {
                Icon(Icons.Default.Check, contentDescription = "Check")
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    onEvent(
                        AddEditEvent.onTitleChange(it)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Title")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = description ?: "",
                onValueChange = {
                    onEvent(
                        AddEditEvent.onDescriptionChange(it)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Description (Optional)")
                }
            )
        }
    }
}

@Preview
@Composable
private fun AddEditContentPreview() {
    TwodoTheme {
        AddEditScreenContent(
            title = "",
            description = null,
            onEvent = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}