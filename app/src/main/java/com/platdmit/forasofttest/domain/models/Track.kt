package com.platdmit.forasofttest.domain.models

data class Track(
    val name: String,
    val explicitness: Boolean,
    val disk: Int,
    val number: Int,
    val timeMillis: String
)