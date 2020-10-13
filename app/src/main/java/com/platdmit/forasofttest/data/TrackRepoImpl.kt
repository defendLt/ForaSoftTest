package com.platdmit.forasofttest.data

import com.platdmit.forasofttest.data.retrofit.ApiTracksRepo
import com.platdmit.forasofttest.data.retrofit.models.ApiTrack
import com.platdmit.forasofttest.data.room.dao.TrackDao
import com.platdmit.forasofttest.data.room.entity.DbTrack
import com.platdmit.forasofttest.domain.converters.TrackConverter
import com.platdmit.forasofttest.domain.models.Album
import com.platdmit.forasofttest.domain.models.Track
import com.platdmit.forasofttest.domain.repositories.TrackRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TrackRepoImpl(
    private val apiTracksRepo: ApiTracksRepo,
    private val dbTrackRepo: TrackDao,
    private val trackConverter: TrackConverter<DbTrack, ApiTrack, Track>
) : TrackRepo {
    override suspend fun getTracksForAlbum(album: Album): Flow<List<Track>> = flow {
        var dbTracks = dbTrackRepo.getTracksForAlbum(album.id)

        if (dbTracks.isEmpty()) {
            val apiTracks = apiTracksRepo.getTrackForAlbum(album.id)

            dbTracks = apiTracks
                .filter { it.trackID > 0 && it.trackName.isNotEmpty() }
                .map { trackConverter.fromApiToDb(it) }

            dbTrackRepo.insertList(dbTracks)
        }

        emit(dbTracks.map { trackConverter.fromDbToDomain(it) })

    }.flowOn(Dispatchers.IO)
}