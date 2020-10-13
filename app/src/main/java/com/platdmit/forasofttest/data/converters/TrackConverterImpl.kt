package com.platdmit.forasofttest.data.converters

import android.annotation.SuppressLint
import com.platdmit.forasofttest.data.retrofit.models.ApiTrack
import com.platdmit.forasofttest.data.room.entity.DbTrack
import com.platdmit.forasofttest.domain.converters.TrackConverter
import com.platdmit.forasofttest.domain.models.Track
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

class TrackConverterImpl
@Inject
constructor() : TrackConverter<DbTrack, ApiTrack, Track> {
    override fun fromApiToDb(apiModel: ApiTrack): DbTrack {
        return DbTrack(
            apiModel.trackID,
            apiModel.collectionID,
            apiModel.trackName,
            explicitnessConvert(apiModel.trackExplicitness),
            apiModel.discNumber,
            apiModel.trackNumber,
            apiModel.trackTimeMillis
        )
    }

    @ExperimentalTime
    override fun fromDbToDomain(dbModel: DbTrack): Track {
        return Track(
            dbModel.name,
            dbModel.explicitness,
            dbModel.disk,
            dbModel.number,
            timeMillisConvert(dbModel.timeMillis)
        )
    }

    private fun explicitnessConvert(explicitness: String): Boolean =
        explicitness == "explicit"

    /**
     * formatting with minSdkVersion 16 support
     */
    @SuppressLint("SimpleDateFormat")
    @ExperimentalTime
    private fun timeMillisConvert(time: Long): String {
        val timePattern = if (time.milliseconds.inHours >= 1){
            "HH:mm:ss"
        } else {
            "mm:ss"
        }
        return SimpleDateFormat(timePattern).format(Date(time))
    }
}