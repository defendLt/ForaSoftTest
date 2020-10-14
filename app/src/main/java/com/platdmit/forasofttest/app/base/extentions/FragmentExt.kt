package com.platdmit.forasofttest.app.base.extentions

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.platdmit.forasofttest.R
import com.platdmit.forasofttest.app.utilities.ProgressBarHandler

/**
 * Base handlers for alert messages
 **/
fun Fragment.showResultMessage(message: String) {
    view?.let {
        Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
    }
}
fun Fragment.showResultMessage(messageId: Int) {
    view?.let {
        Snackbar.make(it, messageId, Snackbar.LENGTH_LONG).show()
    }
}
fun Fragment.showActionResultMessage(message: String, action: () -> Unit) {
    view?.let {
        val actSnackbar =  Snackbar.make(it, message, Snackbar.LENGTH_INDEFINITE)
        actSnackbar.setAction(R.string.retry_button){ action.invoke() }
        actSnackbar.show()
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