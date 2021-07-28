package com.soares.task.data.cache.mappers

import com.soares.task.data.cache.tables.TaskCache
import com.soares.task.domain.models.Task
import com.soares.task.domain.utils.EntityMapper
import javax.inject.Inject

class TaskCacheMapper
@Inject
constructor() : EntityMapper<TaskCache, Task> {

    override fun mapFromEntity(entity: TaskCache): Task {
        return Task(
            id = entity.id,
            description = entity.description,
            priorityId = entity.priorityId,
            dueDate = entity.dueDate,
            complete = entity.complete,
        )
    }

    override fun mapToEntity(domainModel: Task): TaskCache {
        return TaskCache(
            id = domainModel.id,
            description = domainModel.description,
            priorityId = domainModel.priorityId,
            dueDate = domainModel.dueDate,
            complete = domainModel.complete,
        )
    }

    override fun mapFromEntityList(entities: List<TaskCache>?): List<Task> {
        if (entities.isNullOrEmpty()) return arrayListOf()

        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(domainModels: List<Task>?): List<TaskCache> {
        if (domainModels.isNullOrEmpty()) return arrayListOf()

        return domainModels.map { mapToEntity(it) }
    }
}
