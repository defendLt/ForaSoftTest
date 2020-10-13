package com.platdmit.forasofttest.data.utilities

import android.os.Parcel
import android.os.Parcelable

data class PagingSearchKeys(
    val searchString: String,
    var pageNum: Int = 0
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(searchString)
        parcel.writeInt(pageNum)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PagingSearchKeys> {
        override fun createFromParcel(parcel: Parcel): PagingSearchKeys {
            return PagingSearchKeys(parcel)
        }

        override fun newArray(size: Int): Array<PagingSearchKeys?> {
            return arrayOfNulls(size)
        }
    }
}