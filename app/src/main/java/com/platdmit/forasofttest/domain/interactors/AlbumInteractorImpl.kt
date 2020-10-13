package com.platdmit.forasofttest.domain.interactors

import com.platdmit.forasofttest.domain.models.Album
import com.platdmit.forasofttest.domain.models.Track
import com.platdmit.forasofttest.domain.repositories.TrackRepo
import com.platdmit.forasofttest.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlbumInteractorImpl(
    private val trackRepo: TrackRepo,
) : AlbumInteractor {
    override suspend fun getTracks(album: Album): Flow<DataState<List<Track>>> {
        return trackRepo.getTracksForAlbum(album).map { tracks ->
            //Sort tracks by disk and number
            DataState.Success(tracks.sortedWith(compareBy({ it.disk }, { it.number })))
        }
    }
}