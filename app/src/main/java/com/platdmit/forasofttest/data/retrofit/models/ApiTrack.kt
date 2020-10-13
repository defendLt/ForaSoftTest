package com.platdmit.forasofttest.data.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiTrack (
    @SerializedName("artistId")
    @Expose
    val artistID: Long,
    @SerializedName("collectionId")
    @Expose
    val collectionID: Long,
    @SerializedName("trackId")
    @Expose
    val trackID: Long,
    @SerializedName("trackName")
    @Expose
    val trackName: String,
    @SerializedName("trackExplicitness")
    @Expose
    val trackExplicitness: String,
    @SerializedName("discNumber")
    @Expose
    val discNumber: Int,
    @SerializedName("trackNumber")
    @Expose
    val trackNumber: Int,
    @SerializedName("trackTimeMillis")
    @Expose
    val trackTimeMillis: Long
)