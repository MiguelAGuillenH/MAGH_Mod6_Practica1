package com.magh.mod6practica1.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.magh.mod6practica1.R
import com.magh.mod6practica1.application.PlayMentApp
import com.magh.mod6practica1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Variables for MVVM
    val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as PlayMentApp).repository) //Repository
    }

    //Variables for tracks list
    private lateinit var trackAdapter: TrackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure tracks list
        trackAdapter = TrackAdapter()
        //Click on track
        trackAdapter.onTrackClicked = {track ->
            val dialog = TrackDialog(track)
            dialog.show(supportFragmentManager, "TrackDialog")
        }
        binding.listTracks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = trackAdapter
        }

        //CLick to add track
        binding.btnAddTrack.setOnClickListener {
            val dialog = TrackDialog()
            dialog.show(supportFragmentManager, "TrackDialog")
        }

        //Tracking tracks changes to update UI
        mainViewModel.tracks.observe(this, Observer {tracks ->
            binding.txtNoRegisters.visibility =
                if (tracks.isEmpty()) View.VISIBLE else View.INVISIBLE

            trackAdapter.updateList(tracks)
        })

        //Tracking message changes to show message
        mainViewModel.messageArray.observe(this, Observer { messages ->
            var finalMessage = ""
            messages.forEach {message ->
                val text = message.asString(this@MainActivity).trim()
                if (text.isNotEmpty())
                    finalMessage = "$finalMessage $text"
            }
            Snackbar.make(binding.main, finalMessage.trim(), Snackbar.LENGTH_SHORT)
                .setTextColor(getColor(R.color.PM_white))
                .setBackgroundTint(getColor(R.color.PM_dark_green))
                .show()
        })

        //Show all tracks at start
        mainViewModel.getAllTracks()

    }

}

