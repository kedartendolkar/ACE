package com.kedar.ace.data

import android.content.Context
import com.kedar.ace.R
import com.kedar.ace.utils.NO_INTERNET_ERROR
import com.kedar.ace.utils.UNKNOWN_ERROR


class ErrorHandler {

    fun getErrorMessage(context: Context, errorCode: Int?): String {
        return when (errorCode) {
            UNKNOWN_ERROR -> context.resources.getString(R.string.unknown_error)
            NO_INTERNET_ERROR -> context.resources.getString(R.string.no_internet)
            else -> context.resources.getString(R.string.no_data_found)
        }
    }
}