package com.soares.task.data.cache

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.soares.task.data.cache.daos.TaskDao
import com.soares.task.data.cache.tables.TaskCache
import com.soares.task.shared.Converters
import java.util.*

const val CURRENT_VERSION_DATABASE = 2

@Database(
    entities = [
        TaskCache::class
    ], version = CURRENT_VERSION_DATABASE, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE task ADD COLUMN description TEXT")

                val c = Calendar.getInstance()
                database.execSQL("insert into (id, description, priorityId, dueDate, complete) task values (${c.timeInMillis}, 'Description 1', 3, '30/06/2021', 0)")
                database.execSQL("insert into (id, description, priorityId, dueDate, complete) task values (${c.timeInMillis}, 'Description 2', 2, '05/11/2021', 1)")
                database.execSQL("insert into (id, description, priorityId, dueDate, complete) task values (${c.timeInMillis}, 'Description 3', 1, '30/08/2021', 0)")
                database.execSQL("insert into (id, description, priorityId, dueDate, complete) task values (${c.timeInMillis}, 'Description 4', 2, '22/06/2021', 0)")
                database.execSQL("insert into (id, description, priorityId, dueDate, complete) task values (${c.timeInMillis}, 'Description 5', 3, '21/07/2021', 1)")
                database.execSQL("insert into (id, description, priorityId, dueDate, complete) task values (${c.timeInMillis}, 'Description 6', 2, '15/09/2021', 0)")
                database.execSQL("insert into (id, description, priorityId, dueDate, complete) task values (${c.timeInMillis}, 'Description 7', 1, '30/10/2021', 0)")
                database.execSQL("insert into (id, description, priorityId, dueDate, complete) task values (${c.timeInMillis}, 'Description 8', 1, '01/12/2021', 1)")
                database.execSQL("insert into (id, description, priorityId, dueDate, complete) task values (${c.timeInMillis}, 'Description 9', 3, '08/11/2021', 0)")
            }
        }

        const val DATABASE_NAME: String = "task_db"
    }
}