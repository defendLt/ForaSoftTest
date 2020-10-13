package com.platdmit.forasofttest.app.screens.album

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.platdmit.forasofttest.app.base.BaseViewModel
import com.platdmit.forasofttest.app.utilities.enums.BundleTypes
import com.platdmit.forasofttest.domain.interactors.AlbumInteractor
import com.platdmit.forasofttest.domain.models.Album
import com.platdmit.forasofttest.domain.models.Track
import com.platdmit.forasofttest.domain.state.DataState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlbumViewModel @ViewModelInject
constructor(
    private val albumInteractor: AlbumInteractor,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    val albumLiveData: MutableLiveData<Album> = MutableLiveData()
    val tracksLiveData: MutableLiveData<List<Track>> = MutableLiveData()
    init {
        loaderLiveData.postValue(true)
        viewModelScope.launch {
            savedStateHandle.get<Album>(BundleTypes.ALBUM_DETAIL.name)?.let { album ->
                //Set active album
                albumLiveData.value = album

                //Get tracks for active album
                albumInteractor.getTracks(album)
                    .catch { e -> messageLiveData.postValue(e.localizedMessage) }
                    .collect {
                        when (it) {
                            is DataState.Success<List<Track>> -> {
                                tracksLiveData.postValue(it.data)
                                loaderLiveData.postValue(false)
                            }
                            is DataState.Empty -> {
                                messageLiveData.postValue(it.message)
                            }
                        }
                    }
            }
        }
    }
}