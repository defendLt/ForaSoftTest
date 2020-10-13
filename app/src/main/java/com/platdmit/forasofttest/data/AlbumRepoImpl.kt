package com.platdmit.forasofttest.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import com.platdmit.forasofttest.data.retrofit.ApiAlbumsRepo
import com.platdmit.forasofttest.data.retrofit.models.ApiAlbum
import com.platdmit.forasofttest.data.room.dao.AlbumDao
import com.platdmit.forasofttest.data.room.entity.DbAlbum
import com.platdmit.forasofttest.domain.converters.AlbumConverter
import com.platdmit.forasofttest.domain.models.Album
import com.platdmit.forasofttest.domain.repositories.AlbumRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class AlbumRepoImpl
@ExperimentalPagingApi
constructor(
    private val apiAlbumsRepo: ApiAlbumsRepo,
    private val dbAlbumsRepo: AlbumDao,
    private val albumConverter: AlbumConverter<DbAlbum, ApiAlbum, Album>
) : AlbumRepo, PagingSource<String, Album>() {
    override suspend fun getAlbum(name: String): Flow<List<Album>> = flow {
        var dbAlbums = dbAlbumsRepo.getAlbumsForName(name, 0, 0)
        if (dbAlbums.isEmpty()) {
            val apiAlbums = apiAlbumsRepo.getAlbums(name, 10, 0)
            dbAlbums = apiAlbums.map { albumConverter.fromApiToDb(it) }
            dbAlbumsRepo.insertList(dbAlbums)
        }
        emit(dbAlbums.map { albumConverter.fromDbToDomain(it) })
    }.flowOn(Dispatchers.IO)

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Album> {
        return try {
            var nextPage = params.key?.toInt()?:0

            val dbAlbums = withContext(Dispatchers.IO) {

                //Find albums from bd
                var dbAlbums = dbAlbumsRepo.getAlbumsForName("Meteora", params.loadSize, nextPage*params.loadSize)

                if(dbAlbums.isEmpty()){

                    val apiAlbums = apiAlbumsRepo.getAlbums("Meteora", params.loadSize, nextPage)

                    if (apiAlbums.isEmpty()) {
                        throw Exception("Empty Result")
                    }

                    dbAlbums = apiAlbums.map { albumConverter.fromApiToDb(it) }
                    dbAlbumsRepo.insertList(dbAlbums)
                }

                dbAlbums
            }

            println("albums s: $dbAlbums")
            val albums = dbAlbums.map { albumConverter.fromDbToDomain(it) }.sortedBy { it.name }
            println("albums sortedBy: $albums")
            var prevPage = if(nextPage > 0){
                nextPage.toString()
            } else ""

            nextPage++

            LoadResult.Page(
                albums,
                prevPage,
                "$nextPage"
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception){
            return LoadResult.Error(exception)
        }
    }
}