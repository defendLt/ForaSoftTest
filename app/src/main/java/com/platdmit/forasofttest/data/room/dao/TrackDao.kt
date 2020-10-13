package com.platdmit.forasofttest.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.platdmit.forasofttest.data.room.entity.DbTrack

@Dao
interface TrackDao: BaseDao<DbTrack> {
    @Query("SELECT * FROM dbtrack WHERE albumID = :albumId")
    fun getTracksForAlbum(albumId: Long): List<DbTrack>
}