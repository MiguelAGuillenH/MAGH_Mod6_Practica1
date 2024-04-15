package com.magh.mod6practica1.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magh.mod6practica1.R
import com.magh.mod6practica1.data.MusicRepository
import com.magh.mod6practica1.data.db.model.TrackEntity
import com.magh.mod6practica1.util.StringValue
import com.magh.mod6practica1.util.StringValue.DynamicString
import com.magh.mod6practica1.util.StringValue.StringResource
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(private val repository: MusicRepository): ViewModel() {

    private val _tracks = MutableLiveData<List<TrackEntity>>()
    val tracks: LiveData<List<TrackEntity>> = _tracks

    private val _messageArray = MutableLiveData<List<StringValue>>()
    val messageArray: LiveData<List<StringValue>> = _messageArray

    fun getAllTracks(){
        viewModelScope.launch {
            _tracks.postValue(repository.getAllTracks())
        }
    }

    fun insertTrack(track: TrackEntity){
        try {
            viewModelScope.launch {
                repository.insertTrack(track)
                _tracks.postValue(repository.getAllTracks())
                _messageArray.postValue(listOf(
                    StringResource(R.string.message_success_insert_track)
                ))
            }
        }catch(e: IOException){
            e.printStackTrace()
            _messageArray.postValue(listOf(
                StringResource(R.string.message_error_insert_track),
                DynamicString(e.message.toString())
            ))
        }
    }

    fun updateTrack(track: TrackEntity){
        try {
            viewModelScope.launch {
                repository.updateTrack(track)
                _tracks.postValue(repository.getAllTracks())
                _messageArray.postValue(listOf(
                    StringResource(R.string.message_success_update_track)
                ))
            }
        }catch(e: IOException){
            e.printStackTrace()
            _messageArray.postValue(listOf(
                StringResource(R.string.message_error_update_track),
                DynamicString(e.message.toString())
            ))
        }
    }

    fun deleteTrack(track: TrackEntity){
        try {
            viewModelScope.launch {
                repository.deleteTrack(track)
                _tracks.postValue(repository.getAllTracks())
                _messageArray.postValue(listOf(
                    StringResource(R.string.message_success_delete_track)
                ))
            }
        }catch(e: IOException){
            e.printStackTrace()
            _messageArray.postValue(listOf(
                StringResource(R.string.message_error_delete_track),
                DynamicString(e.message.toString())
            ))
        }
    }

}