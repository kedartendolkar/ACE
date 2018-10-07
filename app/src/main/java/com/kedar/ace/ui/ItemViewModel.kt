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

    /**
     * Get list of items from item repository
     */
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

    /**
     * Get list of items for RecyclerView
     */
    fun getItemList(): LiveData<DataModel> {
        if (allItems == null) {
            allItems = MutableLiveData()
            getItems()
        }
        return allItems as MutableLiveData<DataModel>
    }

    /**
     * Clear the data and call the API again for new set of data
     */
    fun refreshData() {
        allItems?.value = null
        getItems()
    }
}