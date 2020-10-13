package com.platdmit.forasofttest.domain.repositories

import com.platdmit.forasofttest.domain.models.Album
import com.platdmit.forasofttest.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TrackRepo {
    suspend fun getTracksForAlbum(album: Album): Flow<List<Track>>
}