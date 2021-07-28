package com.soares.task.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.soares.task.data.cache.daos.TaskDao
import com.soares.task.data.cache.tables.TaskCache
import com.soares.task.shared.Converters

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
            }
        }

        const val DATABASE_NAME: String = "task_db"
    }
}