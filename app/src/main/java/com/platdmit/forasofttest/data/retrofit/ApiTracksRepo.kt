package com.platdmit.forasofttest.data.retrofit

import com.platdmit.forasofttest.data.retrofit.models.ApiTrack

interface ApiTracksRepo {
    suspend fun getTrackForAlbum(albumId: Long): List<ApiTrack>
}