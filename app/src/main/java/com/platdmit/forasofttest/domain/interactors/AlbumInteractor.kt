package com.platdmit.forasofttest.domain.interactors

import com.platdmit.forasofttest.domain.models.Album
import com.platdmit.forasofttest.domain.models.Track
import com.platdmit.forasofttest.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface AlbumInteractor {
    suspend fun getTracks(album: Album): Flow<DataState<List<Track>>>
}