package com.magh.mod6practica1.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.magh.mod6practica1.util.Constants

@Entity(tableName = Constants.DATABASE_TABLE_TRACKS)
data class TrackEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "track_id")
    val id: Long = 0,

    @ColumnInfo(name = "track_name")
    var name: String = "",

    @ColumnInfo(name = "track_artist")
    var artist: String = "",

    @ColumnInfo(name = "track_album")
    var album: String = "",

    @ColumnInfo(name = "track_genre")
    var genre: String = ""

)