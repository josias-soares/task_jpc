package com.soares.task.data.cache.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
class TaskCache(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "Description")
    var description: String = "",

    @ColumnInfo(name = "PriorityId")
    var priorityId: Int = 0,

    @ColumnInfo(name = "DueDate")
    var dueDate: String = "",

    @ColumnInfo(name = "Complete")
    var complete: Boolean = false
)