package com.kedar.ace.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.kedar.ace.data.ItemRepository
import com.kedar.ace.data.local.entity.DataModel


class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val itemRepository: ItemRepository = ItemRepository(getApplication())

    private var allItems: LiveData<DataModel>? = null

    init {
        getItems()
    }

    /**
     * Get list of items from item repository
     */
    private fun getItems() {
        allItems = itemRepository.getAllItems()
    }

    /**
     * Get list of items for RecyclerView
     */
    fun getItemList(): LiveData<DataModel>? {
        return allItems
    }

    /**
     * Call the API again for new set of data
     */
    fun refreshData() {
        getItems()
    }

    fun destroy() {
        itemRepository.unRegisterObserver()
    }
}