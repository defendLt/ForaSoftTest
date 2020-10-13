package com.platdmit.forasofttest.app.base.extentions

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.picassoRender(url: String) {
    Picasso
        .get()
        .load(url)
        .into(this)
}