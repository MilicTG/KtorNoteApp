package com.androiddevs.ktornoteapp.di

import android.content.Context
import androidx.room.Room
import com.androiddevs.ktornoteapp.data.local.NotesDatabase
import com.androiddevs.ktornoteapp.other.Constants.DATABASE_NAME
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    fun provideNotesDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, NotesDatabase::class.java, DATABASE_NAME)
}