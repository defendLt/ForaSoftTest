package com.platdmit.forasofttest.data.retrofit.impl

import com.platdmit.forasofttest.data.retrofit.ApiTracksRepo
import com.platdmit.forasofttest.data.retrofit.models.ApiTrack
import com.platdmit.forasofttest.data.retrofit.rest.RestTracks

class ApiTracksRepoImpl(
    private val restTracks: RestTracks
) : ApiTracksRepo {
    override suspend fun getTrackForAlbum(albumId: Long): List<ApiTrack> {
        val response = restTracks
            .getTracksForAlbum(
                albumId.toString(),
                QUERY_LIMIT, QUERY_ENTITY
            )
        return response.body()!!.results
    }

    companion object {
        const val QUERY_LIMIT = 200
        const val QUERY_ENTITY = "song"
    }
}