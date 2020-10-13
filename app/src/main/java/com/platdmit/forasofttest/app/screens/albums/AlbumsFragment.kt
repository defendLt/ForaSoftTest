package com.platdmit.forasofttest.app.screens.albums

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.platdmit.forasofttest.R
import com.platdmit.forasofttest.app.base.extentions.getQueryHandlerFlow
import com.platdmit.forasofttest.app.base.extentions.showLoading
import com.platdmit.forasofttest.app.base.extentions.showResultMessage
import com.platdmit.forasofttest.app.utilities.enums.SaveStateKeys
import com.platdmit.forasofttest.databinding.FragmentAlbumsBinding
import com.platdmit.forasofttest.domain.models.Album
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AlbumsFragment : Fragment(R.layout.fragment_albums) {
    private val albumsViewModel: AlbumsViewModel by viewModels()
    private val albumsViewBinding: FragmentAlbumsBinding by viewBinding()
    private val albumsAdapter = AlbumsPagingDataAdapter()

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

//        searchView.query?.let{
//            outState.putCharSequence(SaveStateKeys.QUERY_SAVE_KEY.name, it)
//        }
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

            lifecycleScope.launch {
                listData.collect {
                    albumsAdapter.submitData(it)
                }
            }
        }
    }

    private fun initSearchResultHandler(searchView: SearchView) {
        lifecycleScope.launch {
            searchView.getQueryHandlerFlow(5)
                .debounce(1000)
                .distinctUntilChanged()
                .flowOn(Dispatchers.Default)
                .filter { it.isNotEmpty() }
                .collect { query ->
                    println("getQueryHandlerFlow getQueryHandlerFlow: $query")
                    albumsViewModel.findAlbums(query)
                }
        }
    }

    private fun bindPagerAdapterData(albums: PagingData<Album>) {
//        lifecycleScope.launch {
//            albumsAdapter.submitData(albums)
//        }
    }
}