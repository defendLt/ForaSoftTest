package com.platdmit.forasofttest.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.platdmit.forasofttest.data.room.dao.AlbumDao
import com.platdmit.forasofttest.data.room.dao.TrackDao
import com.platdmit.forasofttest.data.room.entity.DbAlbum
import com.platdmit.forasofttest.data.room.entity.DbTrack

@Database(
    entities = [DbAlbum::class, DbTrack::class],
    version = 1,
    exportSchema = false
)
abstract class DbManager : RoomDatabase() {

    abstract fun albumDao(): AlbumDao
    abstract fun trackDao(): TrackDao

    companion object {
        const val DATABASE_NAME = "fora_soft_test"
    }
}