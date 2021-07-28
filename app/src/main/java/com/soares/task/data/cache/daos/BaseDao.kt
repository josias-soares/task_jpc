package com.soares.task.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T> {


    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     * @return The SQLite row id
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T): Long

    /**
     * Update an object in the database.
     *
     * @param obj the object to be updated.
     * @return The SQLite row id
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(obj: T): Int

}