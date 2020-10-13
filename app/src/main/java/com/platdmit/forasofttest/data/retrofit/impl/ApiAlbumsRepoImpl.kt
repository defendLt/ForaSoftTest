package com.platdmit.forasofttest.data.retrofit.impl

import com.platdmit.forasofttest.data.retrofit.ApiAlbumsRepo
import com.platdmit.forasofttest.data.retrofit.models.ApiAlbum
import com.platdmit.forasofttest.data.retrofit.rest.RestAlbums

class ApiAlbumsRepoImpl(
    private val restAlbums: RestAlbums
) : ApiAlbumsRepo {
    override suspend fun getAlbums(term: String, limit: Int, page: Int): List<ApiAlbum> {
        val response =
            restAlbums.getAlbums(
                QUERY_MEDIA, QUERY_ENTITY, QUERY_ATTR,
                term.replace(" ", "+"), limit,
                limit*page
            )
        return response.body()!!.results
    }

    companion object{
        const val QUERY_MEDIA = "music"
        const val QUERY_ENTITY = "album"
        const val QUERY_ATTR = "albumTerm"
    }
}