package com.platdmit.forasofttest.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(
    foreignKeys = [ForeignKey(
        entity = DbAlbum::class,
        parentColumns = ["id"],
        childColumns = ["albumID"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class DbTrack(
    @PrimaryKey @NotNull val id: Long,
    @ColumnInfo(index = true) val albumID: Long,
    val name: String,
    val explicitness: Boolean,
    val disk: Int,
    val number: Int,
    val timeMillis: Long
)