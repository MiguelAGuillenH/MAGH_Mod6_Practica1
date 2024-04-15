package com.magh.mod6practica1.ui

import androidx.appcompat.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.magh.mod6practica1.R
import com.magh.mod6practica1.data.db.model.TrackEntity
import com.magh.mod6practica1.databinding.TrackDialogBinding
import com.magh.mod6practica1.util.MaterialSpinnerAdapter
import com.magh.mod6practica1.util.hideKeyboard


class TrackDialog(
    private var track: TrackEntity = TrackEntity()
): DialogFragment() {

    private var _binding: TrackDialogBinding? = null
    private val binding get() = _binding!!

    //Dialog variables
    private lateinit var builder: MaterialAlertDialogBuilder
    private lateinit var dialog: Dialog

    //MVVM
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = TrackDialogBinding.inflate(requireActivity().layoutInflater)

        builder = MaterialAlertDialogBuilder(requireContext(), R.style.PlayMent_Dialog)

        mainViewModel = (activity as MainActivity).mainViewModel

        //Configure comTrackGenre
        val genres = mapOf(
            "classical" to requireContext().getString(R.string.label_genre_classical),
            "disco" to requireContext().getString(R.string.label_genre_disco),
            "indie" to requireContext().getString(R.string.label_genre_indie),
            "jazz" to requireContext().getString(R.string.label_genre_jazz),
            "metal" to requireContext().getString(R.string.label_genre_metal),
            "new_wave" to requireContext().getString(R.string.label_genre_new_wave),
            "pop" to requireContext().getString(R.string.label_genre_pop),
            "punk" to requireContext().getString(R.string.label_genre_punk),
            "rap" to requireContext().getString(R.string.label_genre_rap),
            "reggae" to requireContext().getString(R.string.label_genre_reggae),
            "rock" to requireContext().getString(R.string.label_genre_rock),
            "ska" to requireContext().getString(R.string.label_genre_ska)
        )

        val adapter = MaterialSpinnerAdapter(
            requireContext(),
            R.layout.material_spinner_item,
            genres.values.toTypedArray()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.comTrackGenre.setAdapter(adapter)

        //Show track info
        binding.apply {
            txtTrackName.setText(track.name)
            txtTrackArtist.setText(track.artist)
            txtTrackAlbum.setText(track.album)
            comTrackGenre.setText(genres.getOrDefault(track.genre, ""), false)
        }

        dialog = if(track.id==(0).toLong())
            //New track
            buildDialog(
                getString(R.string.label_add_track),
                getString(R.string.label_save),
                getString(R.string.label_cancel),
                {
                    //Save button click
                    track.apply {
                        name = binding.txtTrackName.text.toString()
                        artist = binding.txtTrackArtist.text.toString()
                        album = binding.txtTrackAlbum.text.toString()
                        genre = genres.filterValues {
                            it == binding.comTrackGenre.text.toString()
                        }.keys.first().toString()
                    }
                    mainViewModel.insertTrack(track)
                },
                {
                    //Cancel button click
                })
        else
            //Update track
            buildDialog(
                getString(R.string.label_update_track),
                getString(R.string.label_update),
                getString(R.string.label_delete),
                {
                    //Update button click
                    track.apply {
                        name = binding.txtTrackName.text.toString()
                        artist = binding.txtTrackArtist.text.toString()
                        album = binding.txtTrackAlbum.text.toString()
                        genre = genres.filterValues {
                            it == binding.comTrackGenre.text.toString()
                        }.keys.first().toString()
                    }
                    mainViewModel.updateTrack(track)
                },
                {
                    //Delete button click
                    MaterialAlertDialogBuilder(requireContext(), R.style.PlayMent_Dialog)
                        .setTitle(getString(R.string.label_confirmation))
                        .setMessage(getString(R.string.message_confirm_track_delete, track.name))
                        .setPositiveButton(getString(R.string.label_delete)){ _, _ ->
                            mainViewModel.deleteTrack(track)
                        }
                        .setNegativeButton(getString(R.string.label_cancel)){ dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                })

        return dialog
    }

    override fun onStart() {
        super.onStart()

        //comTrackGenre behaviour
        binding.comTrackGenre.apply{
            setOnFocusChangeListener { v, hasFocus ->
                if(hasFocus){
                    requireContext().hideKeyboard(v)
                }
            }
        }

        //Validation
        val alertDialog = dialog as AlertDialog
        val saveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        saveButton?.isEnabled = false

        binding.apply {
            txtTrackName.addTextChangedListener {
                saveButton?.isEnabled = validateFields()
            }
            txtTrackAlbum.addTextChangedListener {
                saveButton?.isEnabled = validateFields()
            }
            txtTrackArtist.addTextChangedListener {
                saveButton?.isEnabled = validateFields()
            }
            comTrackGenre.addTextChangedListener {
                saveButton?.isEnabled = validateFields()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun validateFields(): Boolean =
        (binding.txtTrackName.text.toString().trim().isNotEmpty() &&
            binding.txtTrackArtist.text.toString().trim().isNotEmpty() &&
            binding.txtTrackAlbum.text.toString().trim().isNotEmpty() &&
            binding.comTrackGenre.text.toString().trim().isNotEmpty())

    private fun buildDialog(
        title: String,
        positiveText: String,
        negativeText: String,
        positiveButton: () -> Unit,
        negativeButton: () -> Unit
    ): Dialog =
        builder.setView(binding.root)
            .setTitle(title)
            .setPositiveButton(positiveText) { _, _ ->
                positiveButton()
            }
            .setNegativeButton(negativeText) { _, _ ->
                negativeButton()
            }
            .create()

}

