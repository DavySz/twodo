package com.studies.twodo.domain

data class Todo(
    val id: Long,
    val title: String,
    val description: String?,
    val isFinished: Boolean
)

// fake objects

val fakeTodoUnfinished = Todo(
    id = 1,
    title = "Pay the credit card",
    description = "Pay mastercard credit card",
    isFinished = false
)

val fakeTodoFinished = Todo(
    id = 1,
    title = "Pay the credit card",
    description = "Pay mastercard credit card",
    isFinished = true
)