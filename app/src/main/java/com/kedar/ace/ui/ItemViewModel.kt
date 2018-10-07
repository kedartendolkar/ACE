package com.kedar.ace.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.kedar.ace.data.ItemRepository
import com.kedar.ace.data.ResponseNotifier
import com.kedar.ace.model.DataModel
import retrofit2.Response


class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val itemRepository: ItemRepository = ItemRepository()

    private var allItems: MutableLiveData<DataModel>? = null

    init {
        getItems()
    }

    private fun getItems() {
        itemRepository.getAllItems(object : ResponseNotifier {
            override fun onSuccess(response: Any?) {
                val body: Any? = (response as Response<*>).body()
                if (body is DataModel) {
                    allItems!!.value = body
                }
            }

            override fun onFailure() {
                allItems!!.value = null
            }
        })
    }

    fun getItemList(): LiveData<DataModel> {
        if (allItems == null) {
            allItems = MutableLiveData()
            getItems()
        }
        return allItems as MutableLiveData<DataModel>
    }

    fun refreshData() {
        allItems?.value = null
        getItems()
    }
}