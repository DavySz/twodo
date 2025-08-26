package com.studies.twodo.ui.features.list

sealed interface ListEvent {
    data class delete(val id: Long) : ListEvent
    data class onFinishTask(val id: Long, val isFinished: Boolean) : ListEvent
    data class addEdit(val id: Long?) : ListEvent
}