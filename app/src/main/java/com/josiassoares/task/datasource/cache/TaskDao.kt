package com.josias.soares.task.datasource.cache

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TaskDao : BaseDao<TaskCache> {

    @Query("SELECT * FROM task LIMIT 1")
    suspend fun get(): TaskCache

    @Query("SELECT * FROM task")
    suspend fun getAll(): List<TaskCache>

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getById(id: Long): TaskCache

    @Query("UPDATE task set obs = :obs WHERE id = :id")
    suspend fun updateObs(id: Long, obs: String): Int
}