package com.studies.twodo.ui.features.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studies.twodo.data.TodoRepository
import com.studies.twodo.navigation.AddEditRoute
import com.studies.twodo.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val todos = repository.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.delete -> {
                deleteTodo(event.id)
            }
            is ListEvent.addEdit -> {
                navigateToAddEdit(event)
            }
            is ListEvent.onFinishTask -> {
                finishTask(event.id, event.isFinished)
            }
        }
    }

    private fun deleteTodo(id: Long) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }

    private fun finishTask(id: Long, isFinished: Boolean) {
        viewModelScope.launch {
            repository.updateIsFinished(id, isFinished)
        }
    }

    private fun navigateToAddEdit(event: ListEvent.addEdit){
        viewModelScope.launch {
            _uiEvent.send(UiEvent.navigate(AddEditRoute(event.id)))
        }
    }
}