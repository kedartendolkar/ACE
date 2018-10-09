package com.kedar.ace.data

import android.app.Application
import com.kedar.ace.model.DataModel
import com.kedar.ace.utils.NO_INTERNET_ERROR
import com.kedar.ace.utils.UNKNOWN_ERROR
import com.kedar.ace.utils.Utils
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


class ItemRepository(var application: Application) : BaseRepository() {

    /**
     * Get all items form API
     */
    fun getAllItems(responseNotifier: ResponseNotifier) {
        if (Utils(application).isNetworkConnected()) {
            apiService.getItems().enqueue(object : Callback, retrofit2.Callback<DataModel> {
                override fun onFailure(call: Call<DataModel>?, t: Throwable?) {
                    responseNotifier.onFailure(UNKNOWN_ERROR)
                }

                override fun onResponse(call: Call<DataModel>?, response: Response<DataModel>?) {
                    responseNotifier.onSuccess(response?.body() as DataModel)
                }
            })
        } else {
            responseNotifier.onFailure(NO_INTERNET_ERROR)
        }
    }
}