package com.platdmit.forasofttest.app.screens.albums

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.platdmit.forasofttest.app.base.BaseViewModel
import com.platdmit.forasofttest.data.AlbumRepoImpl
import com.platdmit.forasofttest.domain.interactors.AlbumsInteractor
import com.platdmit.forasofttest.domain.models.Album
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AlbumsViewModel @ViewModelInject
constructor(
    private val albumsInteractor: AlbumsInteractor,
    private val albumRepoImpl: AlbumRepoImpl,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private var searchJob: Job? = null
    val albumsLiveData: MutableLiveData<List<Album>> = MutableLiveData()
//    val albumsPagingData: Flow<PagingData<Album>> = albumsInteractor.getPagingSource("769") as? Flow<PagingData<Album>>
//    get() {
//       return Pager(PagingConfig(pageSize = 15, prefetchDistance = 10, enablePlaceholders = true)) {
//            albumRepoImpl
//        }.flow.cachedIn(viewModelScope).catch { fall ->
//            println("AlbumInteractor fall ${fall}")
//        }
//    }
    val listData = Pager(PagingConfig(pageSize = 10, prefetchDistance = 15, initialLoadSize = 20, enablePlaceholders = true)) {
        albumRepoImpl
    }.flow.cachedIn(viewModelScope).catch { e ->
    println("AlbumsViewModellistData ${e.localizedMessage}")
        messageLiveData.postValue(e.localizedMessage)
    }
    init {
        loaderLiveData.postValue(false)
//        findAlbums("Hybrid+Theory")
    }
    fun findAlbums(term: String){
//        searchJob?.cancel()
//        searchJob = viewModelScope.launch {
//            albumsInteractor.searchAlbums(term)
//                .onEach { albums ->
//                    println("albumsInteractoralbums $albums")
//                    albumsLiveData.value = albums
//                }
//                .catch { e ->
//                    println("albumsInteractoralbums catch ${e.localizedMessage}")
//                    messageLiveData.postValue(e.localizedMessage)
//                }
//                .launchIn(viewModelScope)
//        }
    }
}