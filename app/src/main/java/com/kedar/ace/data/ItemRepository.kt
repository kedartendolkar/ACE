package com.kedar.ace.data

import com.kedar.ace.model.DataModel
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


class ItemRepository : BaseRepository() {

    /**
     * Get all items form API
     */
    fun getAllItems(responseNotifier: ResponseNotifier) {
        apiService.getItems().enqueue(object : Callback, retrofit2.Callback<DataModel> {
            override fun onFailure(call: Call<DataModel>?, t: Throwable?) {
                responseNotifier.onFailure()
            }

            override fun onResponse(call: Call<DataModel>?, response: Response<DataModel>?) {
                responseNotifier.onSuccess(response)
            }

        })
    }
}