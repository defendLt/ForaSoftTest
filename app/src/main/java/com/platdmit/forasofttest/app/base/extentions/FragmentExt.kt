package com.platdmit.forasofttest.app.base.extentions

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.platdmit.forasofttest.app.utilities.ProgressBarHandler

/**
 * Base handler for alert messages
 **/
fun Fragment.showResultMessage(message: String) {
    view?.let {
        Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
    }
}

/**
 * Base handler for control loading hover in parent activity
 **/
fun Fragment.showLoading(status: Boolean) {
    activity?.let {
        (it as? ProgressBarHandler)?.showLoading(status)
    }
}