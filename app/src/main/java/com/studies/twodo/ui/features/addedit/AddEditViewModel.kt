package com.studies.twodo.ui.features.addedit

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studies.twodo.data.TodoRepository
import com.studies.twodo.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class AddEditViewModel(
    private val repository: TodoRepository,
    private val id: Long? = null
) : ViewModel() {

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf<String?>(null)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        id?.let {
            viewModelScope.launch {
                val todo = repository.getById(id)
                title = todo?.title ?: ""
                description = todo?.description
            }
        }
    }

    fun onEvent(event: AddEditEvent){
        when (event) {
            is AddEditEvent.onTitleChange -> {
                title = event.title
            }
            is AddEditEvent.onDescriptionChange -> {
                description = event.description
            }
            is AddEditEvent.save -> saveTodo()
        }
    }

    private fun saveTodo() {
        viewModelScope.launch {
            if(title.isBlank()){
                _uiEvent.send(UiEvent.showSnackbar("Title cannot be empty!"))
                return@launch
            }

            repository.insert(title = title, description = description, id = id)
            _uiEvent.send(UiEvent.goBack)
        }
    }
}