package com.soares.task.di

import android.content.Context
import androidx.room.Room
import com.soares.task.data.cache.TaskDatabase
import com.soares.task.data.cache.TaskDatabase.Companion.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): TaskDatabase {
        return Room
            .databaseBuilder(
                context,
                TaskDatabase::class.java,
                TaskDatabase.DATABASE_NAME
            )
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()

//        runBlocking {
//            db.taskDao().insert(TaskCache(1, "Description 1", 3, "30/06/2021", false))
//            db.taskDao().insert(TaskCache(2, "Description 2", 2, "05/11/2021", false))
//            db.taskDao().insert(TaskCache(3, "Description 3", 1, "30/08/2021", false))
//            db.taskDao().insert(TaskCache(4, "Description 4", 2, "22/06/2021", false))
//            db.taskDao().insert(TaskCache(5, "Description 5", 3, "21/07/2021", false))
//            db.taskDao().insert(TaskCache(6, "Description 6", 2, "15/09/2021", false))
//            db.taskDao().insert(TaskCache(7, "Description 7", 1, "30/10/2021", false))
//            db.taskDao().insert(TaskCache(8, "Description 8", 1, "01/12/2021", false))
//            db.taskDao().insert(TaskCache(9, "Description 9", 3, "08/11/2021", false))
//        }
    }
}