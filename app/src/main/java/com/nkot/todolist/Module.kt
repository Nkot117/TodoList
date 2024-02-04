package com.nkot.todolist

import android.content.Context
import androidx.room.Room
import com.nkot.todolist.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()

    @Singleton
    @Provides
    fun provideTaskDao(database: AppDatabase) = database.taskDao()
}
