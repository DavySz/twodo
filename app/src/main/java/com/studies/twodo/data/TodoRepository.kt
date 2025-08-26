package com.studies.twodo.data
import com.studies.twodo.domain.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun insert(title: String, description: String?, id: Long? = null)

    suspend fun updateIsFinished(id: Long, isFinished: Boolean)

    suspend fun delete(id: Long)

    fun getAll(): Flow<List<Todo>>

    suspend fun getById(id: Long): Todo?
}