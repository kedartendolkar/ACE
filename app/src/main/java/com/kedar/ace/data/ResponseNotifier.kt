package com.kedar.ace.data


interface ResponseNotifier {

    fun onSuccess(response: Any?)

    fun onFailure()
}