package com.example.mipt_4_praktinis.state

import android.content.Context
import androidx.room.Room
import com.example.mipt_4_praktinis.data.NoteDatabase
import com.example.mipt_4_praktinis.data.NoteRepository

object Graph {
    lateinit var database: NoteDatabase
    val noteRepository by lazy {
        NoteRepository(noteDAO = database.noteDAO())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, NoteDatabase::class.java, "notelist.db").build()
    }
}