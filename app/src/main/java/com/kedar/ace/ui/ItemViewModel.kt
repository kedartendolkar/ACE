package com.kedar.ace.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.kedar.ace.data.ItemRepository
import com.kedar.ace.data.ResponseNotifier
import com.kedar.ace.model.DataModel
import com.kedar.ace.utils.NO_ERROR


class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val itemRepository: ItemRepository = ItemRepository(getApplication())

    private var allItems: MutableLiveData<DataModel>? = null

    private var error: MutableLiveData<Int>? = null

    init {
        error = MutableLiveData()
        error?.value = NO_ERROR
    }

    /**
     * Get list of items from item repository
     */
    private fun getItems() {
        itemRepository.getAllItems(object : ResponseNotifier {
            override fun onSuccess(response: Any?) {
                if (response is DataModel) {
                    allItems!!.value = response
                    error?.value = NO_ERROR
                }
            }

            override fun onFailure(errorCode: Int) {
                allItems!!.value = null
                error?.value = errorCode
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
     * Get list of items for RecyclerView
     */
    fun getError(): LiveData<Int> {
        return error as MutableLiveData<Int>
    }

    /**
     * Call the API again for new set of data
     */
    fun refreshData() {
        getItems()
    }
}