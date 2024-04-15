package com.magh.mod6practica1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.magh.mod6practica1.data.db.model.TrackEntity
import com.magh.mod6practica1.databinding.TrackItemBinding

class TrackAdapter: RecyclerView.Adapter<TrackViewHolder>() {

    private var tracks: List<TrackEntity> = emptyList()

    var onTrackClicked: ((TrackEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = TrackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding)
    }

    override fun getItemCount(): Int =
        tracks.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {

        val track = tracks[position]

        holder.bind(track)

        holder.itemView.setOnClickListener{
            onTrackClicked?.invoke(track)
        }

    }

    fun updateList(list: List<TrackEntity>){
        tracks = list
        notifyDataSetChanged()
    }

}