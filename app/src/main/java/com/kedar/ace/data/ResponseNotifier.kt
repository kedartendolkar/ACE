package com.kedar.ace.data

/**
 * Interface callback for API response
 */
interface ResponseNotifier {

    /**
     * Success callback for API response
     */
    fun onSuccess(response: Any?)

    /**
     * Failure callback for API response
     */
    fun onFailure(errorCode: Int)
}