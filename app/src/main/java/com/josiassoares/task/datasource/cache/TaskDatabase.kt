package com.josiassoares.task.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.josiassoares.task.shared.Converters

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
                database.execSQL("ALTER TABLE task ADD COLUMN obs TEXT")
            }
        }

        const val DATABASE_NAME: String = "task_db"
    }
}