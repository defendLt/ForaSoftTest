package com.platdmit.forasofttest.app.di

import com.platdmit.forasofttest.data.retrofit.ApiAlbumsRepo
import com.platdmit.forasofttest.data.retrofit.ApiTracksRepo
import com.platdmit.forasofttest.data.retrofit.impl.ApiAlbumsRepoImpl
import com.platdmit.forasofttest.data.retrofit.impl.ApiTracksRepoImpl
import com.platdmit.forasofttest.data.retrofit.rest.RestAlbums
import com.platdmit.forasofttest.data.retrofit.rest.RestTracks
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideOkHttpLogLevel() : HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpInstance(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor{
                val newRequest = it.request()
                    .newBuilder()
                    .build()
                it.proceed(newRequest)
            }
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ) : Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideRestAlbums(
        retrofit: Retrofit.Builder
    ) : RestAlbums {
        return retrofit
            .build()
            .create(RestAlbums::class.java)
    }

    @Singleton
    @Provides
    fun provideRestTracks(
        retrofit: Retrofit.Builder
    ) : RestTracks {
        return retrofit
            .build()
            .create(RestTracks::class.java)
    }

    @Singleton
    @Provides
    fun provideApiAlbumsRepo(
        restAlbums: RestAlbums
    ): ApiAlbumsRepo{
        return ApiAlbumsRepoImpl(restAlbums)
    }

    @Singleton
    @Provides
    fun provideApiTrackRepo(
        restTracks: RestTracks
    ): ApiTracksRepo{
        return ApiTracksRepoImpl(restTracks)
    }
}