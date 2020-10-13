package com.platdmit.forasofttest.data.retrofit.rest

import com.platdmit.forasofttest.data.retrofit.models.ApiResponseBody
import com.platdmit.forasofttest.data.retrofit.models.ApiTrack
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RestTracks {
    @GET("lookup")
    suspend fun getTracksForAlbum(
        @Query("id", encoded = true) albumId: String,
        @Query("limit") limit: Int,
        @Query("entity") entity: String
    ): Response<ApiResponseBody<ApiTrack>>
}