package com.platdmit.forasofttest.domain.repositories

import com.platdmit.forasofttest.domain.models.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepo {
    suspend fun getAlbum(name: String): Flow<List<Album>>
}