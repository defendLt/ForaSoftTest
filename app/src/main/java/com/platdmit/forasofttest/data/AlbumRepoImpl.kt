package com.platdmit.forasofttest.data

import androidx.paging.*
import com.platdmit.forasofttest.data.retrofit.ApiAlbumsRepo
import com.platdmit.forasofttest.data.retrofit.models.ApiAlbum
import com.platdmit.forasofttest.data.room.dao.AlbumDao
import com.platdmit.forasofttest.data.room.entity.DbAlbum
import com.platdmit.forasofttest.data.utilities.PagingSearchKeys
import com.platdmit.forasofttest.domain.converters.AlbumConverter
import com.platdmit.forasofttest.domain.models.Album
import com.platdmit.forasofttest.domain.repositories.AlbumRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class AlbumRepoImpl
@ExperimentalPagingApi
constructor(
    private val apiAlbumsRepo: ApiAlbumsRepo,
    private val dbAlbumsRepo: AlbumDao,
    private val albumConverter: AlbumConverter<DbAlbum, ApiAlbum, Album>
) : AlbumRepo, PagingSource<PagingSearchKeys, Album>() {

    override suspend fun getPagingSearchResult(name: String): Flow<PagingData<Album>> {
        return Pager(PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = LOAD_DISTANCE,
            initialLoadSize = INIT_LOAD_SIZE,
            enablePlaceholders = PLACEHOLDER
        ), initialKey = PagingSearchKeys(name)){
            this
        }.flow
    }

    override suspend fun load(params: LoadParams<PagingSearchKeys>): LoadResult<PagingSearchKeys, Album> {
        return try {

            val actualKeys = params.key!!

            val dbAlbums = withContext(Dispatchers.IO) {

                //Find albums from bd
                var dbAlbums = dbAlbumsRepo.getAlbumsForName(actualKeys.searchString, params.loadSize, actualKeys.pageNum*params.loadSize)

                if(dbAlbums.isEmpty()){

                    val apiAlbums = apiAlbumsRepo.getAlbums(actualKeys.searchString, params.loadSize, actualKeys.pageNum)

                    if (apiAlbums.isEmpty()) {
                        throw Exception(EMPTY_RESULT)
                    }

                    dbAlbums = apiAlbums.map { albumConverter.fromApiToDb(it) }
                    dbAlbumsRepo.insertList(dbAlbums)
                }

                dbAlbums
            }

            val albums = dbAlbums.map { albumConverter.fromDbToDomain(it) }.sortedBy { it.name }

            val nextKeys = actualKeys.copy(pageNum = actualKeys.pageNum+1)

            //For correct paging work first prevKey == null
            LoadResult.Page(
                albums,
                prevKey = if(actualKeys.pageNum == 0) null else actualKeys,
                nextKeys
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception){
            return LoadResult.Error(exception)
        }
    }

    companion object{
        const val PAGE_SIZE = 20
        const val LOAD_DISTANCE = 5
        const val INIT_LOAD_SIZE = 20
        const val PLACEHOLDER = false
        const val EMPTY_RESULT = "Empty Result"
    }
}