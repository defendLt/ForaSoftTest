package com.platdmit.forasofttest.app.base.extentions

import android.view.View

/**
 * Base handler for visibility
 **/
fun View.setVisibilityStatus(status: Boolean){
    this.visibility = if (status) View.VISIBLE else View.GONE
}