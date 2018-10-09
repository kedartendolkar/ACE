package com.kedar.ace.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager


class Utils(private var application: Application) {

    fun isNetworkConnected(): Boolean {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return cm.activeNetworkInfo != null
    }
}