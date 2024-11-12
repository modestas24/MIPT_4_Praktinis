package com.example.mipt_4_praktinis

import android.app.Application
import com.example.mipt_4_praktinis.state.Graph

class NoteListApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}