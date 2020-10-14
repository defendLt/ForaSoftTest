package com.platdmit.forasofttest.app.base.extentions

import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
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
                    hideSystemKeyboard()
                }
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            newText?.let {
                if (it.length >= stringLimit) {
                    searchString.value = it
                }
            }
            return true
        }
    })

    return searchString;
}

/**
 * Hide system keyboard
 **/
fun SearchView.hideSystemKeyboard(){

    context?.let {
        val inputMethodManager: InputMethodManager? = it.getSystemService()
        inputMethodManager?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

}