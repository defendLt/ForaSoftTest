package com.platdmit.forasofttest.domain.repositories

import androidx.paging.PagingData
import com.platdmit.forasofttest.domain.models.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepo {
    suspend fun getPagingSearchResult(name: String): Flow<PagingData<Album>>
}