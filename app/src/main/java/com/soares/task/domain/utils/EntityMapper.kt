package com.soares.task.domain.utils

interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity

    fun mapFromEntityList(entities: List<Entity>?): List<DomainModel>

    fun mapToEntityList(domainModels: List<DomainModel>?): List<Entity>
}