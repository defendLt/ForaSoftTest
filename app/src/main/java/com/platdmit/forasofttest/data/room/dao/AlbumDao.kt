package com.platdmit.forasofttest.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.platdmit.forasofttest.data.room.entity.DbAlbum

@Dao
interface AlbumDao : BaseDao<DbAlbum> {
    @Query("SELECT * FROM dbalbum WHERE name = :name ORDER BY name LIMIT :limit OFFSET :offset")
    suspend fun getAlbumsForName(name: String, limit: Int, offset: Int = 0): List<DbAlbum>
}