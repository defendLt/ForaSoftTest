package com.platdmit.forasofttest.app.di

import com.platdmit.forasofttest.domain.interactors.AlbumInteractor
import com.platdmit.forasofttest.domain.interactors.AlbumInteractorImpl
import com.platdmit.forasofttest.domain.interactors.AlbumsInteractor
import com.platdmit.forasofttest.domain.interactors.AlbumsInteractorImpl
import com.platdmit.forasofttest.domain.repositories.AlbumRepo
import com.platdmit.forasofttest.domain.repositories.TrackRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object InteractorModule {
    @Singleton
    @Provides
    fun provideAlbumsInteractor(
        albumRepo: AlbumRepo
    ): AlbumsInteractor {
        return AlbumsInteractorImpl(albumRepo)
    }
    @Singleton
    @Provides
    fun provideAlbumInteractor(
        trackRepo: TrackRepo
    ):AlbumInteractor{
        return AlbumInteractorImpl(trackRepo)
    }
}