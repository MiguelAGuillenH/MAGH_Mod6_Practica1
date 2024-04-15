package com.magh.mod6practica1.data

import com.magh.mod6practica1.data.db.MusicDAO
import com.magh.mod6practica1.data.db.model.TrackEntity

class MusicRepository(private val musicDAO: MusicDAO) {

    //Create
    suspend fun insertTrack(track: TrackEntity){
        musicDAO.insertTrack(track)
    }

    //Read
    suspend fun getAllTracks(): List<TrackEntity>{
        return musicDAO.getAllTracks()
    }

    //Update
    suspend fun updateTrack(track: TrackEntity){
        musicDAO.updateTrack(track)
    }

    //Delete
    suspend fun deleteTrack(track: TrackEntity){
        musicDAO.deleteTrack(track)
    }

}