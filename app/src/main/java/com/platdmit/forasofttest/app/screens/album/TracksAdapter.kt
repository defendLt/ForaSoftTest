package com.platdmit.forasofttest.app.screens.album

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.platdmit.forasofttest.databinding.FragmentTrackItemBinding
import com.platdmit.forasofttest.domain.models.Track

class TracksAdapter : RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {
    private val tracksList: MutableList<Track> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val dataBinding = FragmentTrackItemBinding.inflate(layoutInflater, parent, false)
        return TracksViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bindData(tracksList[position])
    }

    override fun getItemCount(): Int = tracksList.size

    fun setData(tracks: List<Track>){
        tracksList.clear()
        tracksList.addAll(tracks)
        notifyDataSetChanged()
    }

    inner class TracksViewHolder(
        private val viewBinding: FragmentTrackItemBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bindData(track: Track) {
            viewBinding.run {
                trackNumber.text = track.number.toString()
                trackName.text = track.name
                trackTime.text = track.timeMillis
                if (!track.explicitness) trackExplicit.visibility = View.GONE
            }
        }
    }
}