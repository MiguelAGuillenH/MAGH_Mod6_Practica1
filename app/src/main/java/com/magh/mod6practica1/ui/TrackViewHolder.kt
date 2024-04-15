package com.magh.mod6practica1.ui

import androidx.recyclerview.widget.RecyclerView
import com.magh.mod6practica1.R
import com.magh.mod6practica1.data.db.model.TrackEntity
import com.magh.mod6practica1.databinding.TrackItemBinding

class TrackViewHolder(private var binding: TrackItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(track: TrackEntity){

        binding.apply {
            lblTrack.text = track.name
            lblArtistAlbum.text =
                root.context.getString(R.string.label_artist_album, track.artist, track.album)
            imgGenre.setImageResource(
                root.context.resources.getIdentifier(track.genre, "drawable", root.context.packageName)
            )
        }

    }

}