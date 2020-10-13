package com.platdmit.forasofttest.domain.models

import android.os.Parcel
import android.os.Parcelable

data class Album(
    val id: Long,
    val name: String,
    val artistName: String,
    val artUrl60: String,
    val artUrl100: String,
    val explicitness: Boolean,
    val advisoryRating: String?,
    val trackCount: Int,
    val country: String,
    val releaseDate: String,
    val primaryGenreName: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(artistName)
        parcel.writeString(artUrl60)
        parcel.writeString(artUrl100)
        parcel.writeByte(if (explicitness) 1 else 0)
        parcel.writeString(advisoryRating)
        parcel.writeInt(trackCount)
        parcel.writeString(country)
        parcel.writeString(releaseDate)
        parcel.writeString(primaryGenreName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Album> {
        override fun createFromParcel(parcel: Parcel): Album {
            return Album(parcel)
        }

        override fun newArray(size: Int): Array<Album?> {
            return arrayOfNulls(size)
        }
    }
}