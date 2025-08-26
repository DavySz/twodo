package com.studies.twodo.data

import com.studies.twodo.domain.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dao: TodoDAO
) : TodoRepository {
    override suspend fun insert(title: String, description: String?, id: Long?) {
        val entity = if (id != null) {
            dao.getById(id)?.copy(
                title = title,
                description = description
            )
        } else {
            TodoEntity(
                title = title,
                description = description,
                isFinished = false
            )
        }

        entity?.let { dao.insert(it) }
    }

    override suspend fun updateIsFinished(id: Long, isFinished: Boolean){
        val entityFound = dao.getById(id)
        if(entityFound === null) return;

        val updatedEntity = entityFound.copy(isFinished = isFinished)
        dao.insert(updatedEntity)
    }

    override suspend fun delete(id: Long){
        val entityFound = dao.getById(id)
        if(entityFound === null) return;
        dao.delete(entityFound)
    }

    override fun getAll(): Flow<List<Todo>> {
        return dao.getAll().map {
            it.map { entity ->
                mapTodoEntity(entity)
            }
        }
    }

    override suspend fun getById(id: Long): Todo? {
        return dao.getById(id)?.let { entity ->
            mapTodoEntity(entity)
        }
    }

    private fun mapTodoEntity(entity: TodoEntity): Todo {
       return Todo(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            isFinished = entity.isFinished
        )
    }
}