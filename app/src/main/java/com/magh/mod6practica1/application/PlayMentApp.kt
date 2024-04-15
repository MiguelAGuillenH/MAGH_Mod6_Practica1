package com.magh.mod6practica1.application

import android.app.Application
import com.magh.mod6practica1.data.MusicRepository
import com.magh.mod6practica1.data.db.MusicDatabase

class PlayMentApp: Application() {

    private val database by lazy{
        MusicDatabase.getDatabase(this@PlayMentApp)
    }

    val repository by lazy{
        MusicRepository(database.musicDAO())
    }

}