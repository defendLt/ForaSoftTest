package com.platdmit.forasofttest.app.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.platdmit.forasofttest.data.room.DbManager
import com.platdmit.forasofttest.data.room.dao.AlbumDao
import com.platdmit.forasofttest.data.room.dao.TrackDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): DbManager {
        return Room
            .databaseBuilder(
                context,
                DbManager::class.java,
                DbManager.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideAlbumDao(dbManager: DbManager): AlbumDao {
        return dbManager.albumDao()
    }

    @Singleton
    @Provides
    fun provideTrackDao(dbManager: DbManager): TrackDao {
        return dbManager.trackDao()
    }
}