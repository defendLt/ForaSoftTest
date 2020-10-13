package com.platdmit.forasofttest.app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    val messageLiveData: MutableLiveData<String> = MutableLiveData()
    val loaderLiveData: MutableLiveData<Boolean> = MutableLiveData()
}