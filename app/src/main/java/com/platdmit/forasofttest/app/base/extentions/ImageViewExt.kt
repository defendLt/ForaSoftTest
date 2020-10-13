package com.platdmit.forasofttest.app.base.extentions

import android.widget.ImageView
import com.platdmit.forasofttest.R
import com.squareup.picasso.Picasso

fun ImageView.picassoRender(url: String) {
    Picasso
        .get()
        .load(url)
        .placeholder(R.mipmap.ic_album_hover)
        .into(this)
}