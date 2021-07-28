package com.soares.task.data.network.mappers

import com.soares.task.data.network.TaskNetwork
import com.soares.task.domain.models.Task
import com.soares.task.domain.utils.EntityMapper
import javax.inject.Inject

class TaskNetworkMapper
@Inject
constructor() : EntityMapper<TaskNetwork, Task> {
    override fun mapFromEntity(entity: TaskNetwork): Task {
        return Task(
            id = entity.id,
            description = entity.description,
            priorityId = entity.priorityId,
            dueDate = entity.dueDate,
            complete = entity.complete,
        )
    }

    override fun mapToEntity(domainModel: Task): TaskNetwork {
        return TaskNetwork(
            id = domainModel.id,
            description = domainModel.description,
            priorityId = domainModel.priorityId,
            dueDate = domainModel.dueDate,
            complete = domainModel.complete,
        )
    }

    override fun mapFromEntityList(entities: List<TaskNetwork>?): List<Task> {
        if (entities.isNullOrEmpty()) return arrayListOf()

        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(domainModels: List<Task>?): List<TaskNetwork> {
        if (domainModels.isNullOrEmpty())  return arrayListOf()

        return domainModels.map { mapToEntity(it) }
    }
}
