package com.platdmit.forasofttest.data.converters

import android.annotation.SuppressLint
import com.platdmit.forasofttest.data.retrofit.models.ApiAlbum
import com.platdmit.forasofttest.data.room.entity.DbAlbum
import com.platdmit.forasofttest.domain.converters.AlbumConverter
import com.platdmit.forasofttest.domain.models.Album
import java.text.ParseException
import java.text.SimpleDateFormat
import javax.inject.Inject

class AlbumConverterImpl
@Inject
constructor(): AlbumConverter<DbAlbum, ApiAlbum, Album> {
    override fun fromApiToDb(apiModel: ApiAlbum): DbAlbum {
        return DbAlbum(
            apiModel.collectionID,
            apiModel.collectionCensoredName,
            apiModel.artistName,
            apiModel.artworkUrl60,
            apiModel.artworkUrl100,
            apiModel.collectionPrice?:1.0,
            explicitnessConvert(apiModel.collectionExplicitness),
            apiModel.contentAdvisoryRating?:"",
            apiModel.trackCount.toInt(),
            apiModel.country,
            apiModel.releaseDate,
            apiModel.primaryGenreName
        )
    }

    override fun fromDbToDomain(dbModel: DbAlbum): Album {
        return Album(
            dbModel.id,
            dbModel.name,
            dbModel.artistName,
            dbModel.artUrl60,
            dbModel.artUrl100,
            dbModel.explicitness,
            dbModel.advisoryRating,
            dbModel.trackCount,
            dbModel.country,
            releaseDateConvert(dbModel.releaseDate),
            dbModel.primaryGenreName
        )
    }

    private fun explicitnessConvert(explicitness: String): Boolean =
        explicitness == "explicit"


    /**
     * formatting with minSdkVersion 16 support
     */
    @SuppressLint("SimpleDateFormat")
    private fun releaseDateConvert(date: String): String{
        return try {
            val startDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(date)
            SimpleDateFormat("yyyy").format(startDate)
        } catch (e: ParseException){""}
    }
}