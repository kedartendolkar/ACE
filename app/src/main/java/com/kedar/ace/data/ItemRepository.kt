package com.kedar.ace.data

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.kedar.ace.model.DataModel
import com.kedar.ace.utils.NO_INTERNET_ERROR
import com.kedar.ace.utils.UNKNOWN_ERROR
import com.kedar.ace.utils.Utils
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


class ItemRepository(var application: Application) : BaseRepository() {
    val allItems: MutableLiveData<DataModel> = MutableLiveData()

    fun getAllItems(): LiveData<DataModel> {

        if (Utils(application).isNetworkConnected()) {
            apiService.getItems().enqueue(object : Callback, retrofit2.Callback<DataModel> {
                override fun onFailure(call: Call<DataModel>?, t: Throwable?) {
                    allItems.value = DataModel(mError = com.kedar.ace.model.Error(UNKNOWN_ERROR))
                }

                override fun onResponse(call: Call<DataModel>?, response: Response<DataModel>?) {
                    allItems.value = response?.body()
                }
            })
        } else {
            allItems.value = DataModel(mError = com.kedar.ace.model.Error(NO_INTERNET_ERROR))
        }
        return allItems
    }
}