package com.platdmit.forasofttest.app.di

import androidx.paging.ExperimentalPagingApi
import com.platdmit.forasofttest.data.AlbumRepoImpl
import com.platdmit.forasofttest.data.TrackRepoImpl
import com.platdmit.forasofttest.data.converters.AlbumConverterImpl
import com.platdmit.forasofttest.data.converters.TrackConverterImpl
import com.platdmit.forasofttest.data.retrofit.ApiAlbumsRepo
import com.platdmit.forasofttest.data.retrofit.ApiTracksRepo
import com.platdmit.forasofttest.data.room.dao.AlbumDao
import com.platdmit.forasofttest.data.room.dao.TrackDao
import com.platdmit.forasofttest.domain.converters.AlbumConverter
import com.platdmit.forasofttest.domain.converters.TrackConverter
import com.platdmit.forasofttest.domain.repositories.AlbumRepo
import com.platdmit.forasofttest.domain.repositories.TrackRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {
    @ExperimentalPagingApi
    @Singleton
    @Provides
    fun provideAlbumRepo(
        apiAlbumsRepo: ApiAlbumsRepo,
        dbAlbumRepo: AlbumDao,
        albumConverter: AlbumConverterImpl
    ): AlbumRepo {
        return AlbumRepoImpl(
            apiAlbumsRepo,
            dbAlbumRepo,
            albumConverter
        )
    }

    @Singleton
    @Provides
    fun provideTrackRepo(
        apiTracksRepo: ApiTracksRepo,
        dbTrackRepo: TrackDao,
        trackConverter: TrackConverterImpl
    ): TrackRepo {
        return TrackRepoImpl(
            apiTracksRepo,
            dbTrackRepo,
            trackConverter
        )
    }
}