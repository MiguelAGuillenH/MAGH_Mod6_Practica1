package com.magh.mod6practica1.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.magh.mod6practica1.data.db.model.TrackEntity
import com.magh.mod6practica1.util.Constants

@Database(
    entities = [TrackEntity::class],
    version = 1,
    exportSchema = true
)
abstract class MusicDatabase: RoomDatabase() {
    abstract fun musicDAO(): MusicDAO

    companion object{
        @Volatile
        private var INSTANCE: MusicDatabase? = null

        fun getDatabase(context: Context): MusicDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MusicDatabase::class.java,
                    Constants.DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}