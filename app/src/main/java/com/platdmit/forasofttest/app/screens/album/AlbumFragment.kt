package com.platdmit.forasofttest.app.screens.album

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.platdmit.forasofttest.R
import com.platdmit.forasofttest.app.base.extentions.picassoRender
import com.platdmit.forasofttest.app.base.extentions.showLoading
import com.platdmit.forasofttest.app.base.extentions.showResultMessage
import com.platdmit.forasofttest.databinding.FragmentAlbumBinding
import com.platdmit.forasofttest.domain.models.Album
import com.platdmit.forasofttest.domain.models.Track
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumFragment : Fragment(R.layout.fragment_album) {
    private val albumViewModal: AlbumViewModel by viewModels()
    private val albumViewBinding: FragmentAlbumBinding by viewBinding()
    private val tracksAdapter = TracksAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObserves()
    }

    private fun initRecyclerView(){
        albumViewBinding.albumTracks.run {
            layoutManager = LinearLayoutManager(context)
            adapter = tracksAdapter
        }
    }

    private fun initObserves() {
        albumViewModal.run {
            albumLiveData.observe(viewLifecycleOwner, ::bindAlbumData)
            tracksLiveData.observe(viewLifecycleOwner, ::bindAdapterData)
            messageLiveData.observe(viewLifecycleOwner, ::showResultMessage)
            loaderLiveData.observe(viewLifecycleOwner, ::showLoading)
        }
    }

    private fun bindAlbumData(album: Album) {
        albumViewBinding.run {
            albumName.text = album.name
            albumAuthor.text = album.artistName
            albumYear.text = album.releaseDate
            albumGenre.text = album.primaryGenreName
            albumCover.picassoRender(album.artUrl100)
        }
    }

    private fun bindAdapterData(tracks: List<Track>) {
        tracksAdapter.setData(tracks)
    }
}