package com.platdmit.forasofttest.app.screens.albums

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.platdmit.forasofttest.app.base.BaseViewModel
import com.platdmit.forasofttest.domain.models.Album
import com.platdmit.forasofttest.domain.repositories.AlbumRepo
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlbumsViewModel @ViewModelInject
constructor(
    private val albumRepo: AlbumRepo,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private var updatePagingJob: Job? = null
    var albumsLiveData: MutableLiveData<PagingData<Album>> = MutableLiveData()

    init {
        //hide progress bar
        loaderLiveData.postValue(false)
    }

    fun findAlbums(term: String){
        //show progress bar
        loaderLiveData.postValue(true)

        updatePagingJob?.cancel()
        updatePagingJob = viewModelScope.launch {
            albumRepo.getPagingSearchResult(term)
                .cachedIn(viewModelScope)
                .catch { e ->
                    messageLiveData.postValue(e.localizedMessage)
                }.collect {
                    albumsLiveData.postValue(it)

                    //hide progress bar
                    loaderLiveData.postValue(false)
                }
        }
    }
}