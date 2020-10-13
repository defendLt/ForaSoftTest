package com.platdmit.forasofttest.data.retrofit

import com.platdmit.forasofttest.data.retrofit.models.ApiAlbum

interface ApiAlbumsRepo {
    suspend fun getAlbums(term: String, limit: Int = 10, page: Int  = 1): List<ApiAlbum>
}