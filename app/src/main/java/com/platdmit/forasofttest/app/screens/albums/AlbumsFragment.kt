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
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.platdmit.forasofttest.R
import com.platdmit.forasofttest.app.base.extentions.getQueryHandlerFlow
import com.platdmit.forasofttest.app.base.extentions.showActionResultMessage
import com.platdmit.forasofttest.app.base.extentions.showLoading
import com.platdmit.forasofttest.app.base.extentions.showResultMessage
import com.platdmit.forasofttest.app.utilities.enums.BundleTypes
import com.platdmit.forasofttest.app.utilities.enums.SaveStateKeys
import com.platdmit.forasofttest.databinding.FragmentAlbumsBinding
import com.platdmit.forasofttest.domain.models.Album
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AlbumsFragment : Fragment(R.layout.fragment_albums) {
    private val albumsViewModel: AlbumsViewModel by viewModels()
    private val albumsViewBinding: FragmentAlbumsBinding by viewBinding()
    private val albumsAdapter = AlbumsPagingDataAdapter(::recyclerViewClickListener)
    private var actualSearchQuery: String = ""
    private var pagingDataJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Restore search query
        savedInstanceState?.getString(SaveStateKeys.QUERY_SAVE_KEY.name)?.let {
            actualSearchQuery = it
        }

        initRecyclerView()
        initObserves()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.action_bar_albums, menu)

        //Init custom search listener
        (menu.findItem(R.id.search)?.actionView as? SearchView)?.let {
            it.setQuery(actualSearchQuery, false)
            initSearchQueryHandler(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //Save search query
        if(actualSearchQuery.isNotEmpty()){
            outState.putString(SaveStateKeys.QUERY_SAVE_KEY.name, actualSearchQuery)
        }
    }

    private fun initRecyclerView() {
        albumsAdapter.addLoadStateListener(::adapterLoadStateListener)

        albumsViewBinding.run {
            albumsList.layoutManager = LinearLayoutManager(context)
            albumsList.adapter = albumsAdapter.withLoadStateFooter(AlbumsLoadStateAdapter())
            albumsList.setHasFixedSize(true)
        }
    }

    private fun initObserves() {
        albumsViewModel.run {
            messageLiveData.observe(viewLifecycleOwner, ::showResultMessage)
            loaderLiveData.observe(viewLifecycleOwner, ::showLoading)
            albumsLiveData.observe(viewLifecycleOwner, ::initSearchResult)
        }
    }

    private fun initSearchQueryHandler(searchView: SearchView) {
        lifecycleScope.launch {
            searchView.getQueryHandlerFlow()
                .debounce(1000)
                .distinctUntilChanged()
                .filter { it.isNotEmpty() }
                .collect { query ->
                    actualSearchQuery = query
                    if (query.length > 2) {
                        albumsViewModel.findAlbums(query)
                    }
                }
        }
    }

    private fun adapterLoadStateListener(loadStates: CombinedLoadStates){
        val loadState = loadStates.refresh
        if(loadState is LoadState.Error){
            when(loadState.error){
                is IOException,
                is HttpException -> {
                    loadState.error.localizedMessage?.let {
                        showActionResultMessage(it) { albumsAdapter.retry() }
                    }
                }
                else -> loadState.error.localizedMessage?.let (::showResultMessage)
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
        findNavController().navigate(
            R.id.action_albumsFragment_to_albumFragment, bundleOf(
                BundleTypes.ALBUM_DETAIL.name to album
            )
        )
    }
}