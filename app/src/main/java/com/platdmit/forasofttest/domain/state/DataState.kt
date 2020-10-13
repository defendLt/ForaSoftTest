package com.platdmit.forasofttest.domain.state

sealed class DataState<out M> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Empty(val message: String): DataState<Nothing>()
    data class Error(val exception: Exception) : DataState<Nothing>()
}