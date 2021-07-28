package com.soares.task.data.cache.daos

import androidx.room.Dao
import androidx.room.Query
import com.soares.task.data.cache.tables.TaskCache

@Dao
interface TaskDao : BaseDao<TaskCache> {

    @Query("SELECT * FROM task LIMIT 1")
    suspend fun get(): TaskCache

    @Query("DELETE from task")
    suspend fun deleteAll()

    @Query("DELETE from task WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM task")
    suspend fun getAll(): List<TaskCache>

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getById(id: Long): TaskCache
}