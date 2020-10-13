package com.platdmit.forasofttest.data.retrofit.rest

import com.platdmit.forasofttest.data.retrofit.models.ApiAlbum
import com.platdmit.forasofttest.data.retrofit.models.ApiResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RestAlbums {
    @GET("search")
    suspend fun getAlbums(
        @Query("media") media: String,
        @Query("entity") entity: String,
        @Query("attribute") attribute: String,
        @Query("term", encoded = true) term: String,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
    ): Response<ApiResponseBody<ApiAlbum>>
}