package com.platdmit.forasofttest.app.screens.albums

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.platdmit.forasofttest.R
import com.platdmit.forasofttest.app.base.extentions.getQueryHandlerFlow
import com.platdmit.forasofttest.app.base.extentions.showLoading
import com.platdmit.forasofttest.app.base.extentions.showResultMessage
import com.platdmit.forasofttest.app.utilities.enums.BundleTypes
import com.platdmit.forasofttest.app.utilities.enums.SaveStateKeys
import com.platdmit.forasofttest.databinding.FragmentAlbumsBinding
import com.platdmit.forasofttest.domain.models.Album
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AlbumsFragment : Fragment(R.layout.fragment_albums) {
    private val albumsViewModel: AlbumsViewModel by viewModels()
    private val albumsViewBinding: FragmentAlbumsBinding by viewBinding()
    private val albumsAdapter = AlbumsPagingDataAdapter(::recyclerViewClickListener)
    private var pagingDataJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObserves()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.action_bar_albums, menu)

        //Init custom search listener
        (menu.findItem(R.id.search)?.actionView as? SearchView)?.let {
            initSearchResultHandler(it)
        }
    }

    private fun initRecyclerView() {
        albumsViewBinding.run {
            albumsList.layoutManager = LinearLayoutManager(context)
            albumsList.adapter = albumsAdapter
        }
    }

    private fun initObserves() {
        albumsViewModel.run {
            messageLiveData.observe(viewLifecycleOwner, ::showResultMessage)
            loaderLiveData.observe(viewLifecycleOwner, ::showLoading)
            albumsLiveData.observe(viewLifecycleOwner, ::initSearchResult)
        }
    }

    private fun initSearchResultHandler(searchView: SearchView) {
        lifecycleScope.launch {
            searchView.getQueryHandlerFlow(5)
                .debounce(1000)
                .distinctUntilChanged()
                .filter { it.isNotEmpty() }
                .collect { query ->
                    albumsViewModel.findAlbums(query)
                }
        }
    }

    private fun initSearchResult(pagingData: PagingData<Album>){
        pagingDataJob?.cancel()
        pagingDataJob = lifecycleScope.launch {
            albumsViewBinding.albumsList.scrollToPosition(0)
            albumsAdapter.submitData(pagingData)
        }
    }

    private fun recyclerViewClickListener(album: Album){
        view?.findNavController()?.navigate(
            R.id.action_albumsFragment_to_albumFragment, bundleOf(
                BundleTypes.ALBUM_DETAIL.name to album
            )
        )
    }
}