package com.platdmit.forasofttest.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class DbAlbum(
    @PrimaryKey
    @NotNull
    val id: Long,
    val name: String,
    val artistName: String,
    val artUrl60: String,
    val artUrl100: String,
    val price: Double,
    val explicitness: Boolean,
    val advisoryRating: String,
    val trackCount: Int,
    val country: String,
    val releaseDate: String,
    val primaryGenreName: String
)