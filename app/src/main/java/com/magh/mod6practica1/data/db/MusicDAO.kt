package com.magh.mod6practica1.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.magh.mod6practica1.data.db.model.TrackEntity
import com.magh.mod6practica1.util.Constants

@Dao
interface MusicDAO {

    //Create
    @Insert
    suspend fun insertTrack(track: TrackEntity)

    //Read
    @Query("SELECT * FROM ${Constants.DATABASE_TABLE_TRACKS}")
    suspend fun getAllTracks(): List<TrackEntity>

    //Update
    @Update
    suspend fun updateTrack(track: TrackEntity)

    //Delete
    @Delete
    suspend fun deleteTrack(track: TrackEntity)

}