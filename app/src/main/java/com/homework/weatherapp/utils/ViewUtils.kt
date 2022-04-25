package com.homework.weatherapp.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.google.android.material.snackbar.Snackbar

fun View.showErrorSnack(throwable: Throwable) {
    Snackbar.make(
        this,
        throwable.toString(),
        Snackbar.LENGTH_LONG
    ).show()
}


fun loadSvg(context: Context, url: String, imageView: ImageView) {
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }
        .crossfade(true)
        .build()
    val request = ImageRequest.Builder(context)
        .data(url)
        .crossfade(true)
        .target(imageView)
        .build()
    imageLoader.enqueue(request)
}

