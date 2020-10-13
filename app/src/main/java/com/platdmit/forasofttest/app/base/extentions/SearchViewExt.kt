package com.platdmit.forasofttest.app.base.extentions

import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
fun SearchView.getQueryHandlerFlow(stringLimit: Int = 0): StateFlow<String> {

    val searchString = MutableStateFlow("")

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                if (it.length >= stringLimit) {
                    searchString.value = it
                    clearFocus()
                }
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            newText?.let {
                searchString.value = it
            }
            return true
        }
    })

    return searchString;
}