package io.github.justinlewis.app.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import io.github.justinlewis.app.R

/**
 * Created on 5/16/2019.
 */
fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).error(R.drawable.error_image_placeholder)
        .placeholder(R.drawable.image_placeholder).into(this)
}
