package com.androiddevs.ktornoteapp.local

import androidx.room.Database
import androidx.room.TypeConverters
import com.androiddevs.ktornoteapp.local.entitys.Note

@Database(
    entities = [Note::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class NotesDatabase {

    abstract fun noteDao(): NoteDao
}