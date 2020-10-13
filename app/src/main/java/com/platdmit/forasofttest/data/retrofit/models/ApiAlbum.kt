package com.platdmit.forasofttest.data.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiAlbum(
    @SerializedName("wrapperType")
    @Expose
    val wrapperType: String,
    @SerializedName("collectionType")
    @Expose
    val collectionType: String,
    @SerializedName("artistId")
    @Expose
    val artistID: Long,
    @SerializedName("collectionId")
    @Expose
    val collectionID: Long,
    @SerializedName("amgArtistId")
    @Expose
    val amgArtistID: Long,
    @SerializedName("artistName")
    @Expose
    val artistName: String,
    @SerializedName("collectionName")
    @Expose
    val collectionCensoredName: String,
    @SerializedName("artworkUrl60")
    @Expose
    val artworkUrl60: String,
    @SerializedName("artworkUrl100")
    @Expose
    val artworkUrl100: String,
    @SerializedName("collectionPrice")
    @Expose
    val collectionPrice: Double? = null,
    @SerializedName("collectionExplicitness")
    @Expose
    val collectionExplicitness: String,
    @SerializedName("contentAdvisoryRating")
    @Expose
    val contentAdvisoryRating: String? = null,
    @SerializedName("trackCount")
    @Expose
    val trackCount: Long,
    @SerializedName("copyright")
    @Expose
    val copyright: String,
    @SerializedName("country")
    @Expose
    val country: String,
    @SerializedName("currency")
    @Expose
    val currency: String,
    @SerializedName("releaseDate")
    @Expose
    val releaseDate: String,
    @SerializedName("primaryGenreName")
    @Expose
    val primaryGenreName: String
)