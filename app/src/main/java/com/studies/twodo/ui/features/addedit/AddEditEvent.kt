package com.studies.twodo.ui.features.addedit

sealed interface AddEditEvent {
    data class onTitleChange(val title: String) : AddEditEvent
    data class onDescriptionChange(val description: String) : AddEditEvent
    data object save : AddEditEvent
}