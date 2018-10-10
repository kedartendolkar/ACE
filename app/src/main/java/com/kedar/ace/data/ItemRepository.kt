package com.kedar.ace.data

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.os.AsyncTask
import com.kedar.ace.data.local.ItemRoomDatabase
import com.kedar.ace.data.local.dao.ItemDao
import com.kedar.ace.data.local.entity.DataModel
import com.kedar.ace.data.local.entity.Error
import com.kedar.ace.data.local.entity.RowsEntity
import com.kedar.ace.utils.NO_INTERNET_ERROR
import com.kedar.ace.utils.UNKNOWN_ERROR
import com.kedar.ace.utils.Utils
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


class ItemRepository(private var application: Application) : BaseRepository() {
    private val allItems: MutableLiveData<DataModel> = MutableLiveData()
    private var rowEntities: LiveData<List<RowsEntity>>? = MutableLiveData()
    private var dataModel: LiveData<DataModel>? = MutableLiveData()

    private var itemDao: ItemDao? = null

    init {
        val db = ItemRoomDatabase.getInstance(application)

        itemDao = db?.itemDao()

        dataModel = itemDao?.getDataModel()

        dataModel?.observeForever(DataModelObserver())
    }

    /**
     * Observer for ItemDao
     */
    inner class ItemObserver(private val title: String?) : Observer<List<RowsEntity>> {
        override fun onChanged(t: List<RowsEntity>?) {
            allItems.value = DataModel(rowsEntity = t, title = title.orEmpty())
        }
    }

    /**
     * Observer for ItemDao
     */
    inner class DataModelObserver : Observer<DataModel> {
        override fun onChanged(t: DataModel?) {
            val title = t?.title.orEmpty()

            rowEntities = itemDao?.getAllItems()

            rowEntities?.observeForever(ItemObserver(title))
        }
    }

    /**
     * Remove all observers
     */
    fun unRegisterObserver() {
        rowEntities?.removeObserver(ItemObserver(null))
        dataModel?.removeObserver(DataModelObserver())
    }

    /**
     * Get data from network or cache based on network connectivity
     */
    fun getAllItems(): LiveData<DataModel> {

        if (Utils(application).isNetworkConnected()) {
            getApiResponse()
        } else {
            allItems.value = DataModel(error = Error(NO_INTERNET_ERROR),
                    rowsEntity = getRowEntityList(),
                    title = getTitle())
        }
        return allItems
    }

    private fun getRowEntityList() = rowEntities?.value

    private fun getTitle() = dataModel?.value?.title.orEmpty()

    /**
     * Get data from API
     */
    private fun getApiResponse() {
        apiService.getItems().enqueue(object : Callback, retrofit2.Callback<DataModel> {
            override fun onFailure(call: Call<DataModel>?, t: Throwable?) {
                allItems.value = DataModel(error = Error(UNKNOWN_ERROR), rowsEntity = getRowEntityList(), title = getTitle())
            }

            override fun onResponse(call: Call<DataModel>?, response: Response<DataModel>?) {
                val dataModel: DataModel? = response?.body()
                dataModel?.let {
                    insert(it)
                }
            }
        })
    }

    /**
     * Insert data in database
     */
    fun insert(dataModel: DataModel) {
        InsertRowEntityAsyncTask(itemDao).execute(dataModel)
    }

    /**
     * AsyncTask to insertRows data into database
     * Previous data will be cleared before adding
     */
    private class InsertRowEntityAsyncTask internal constructor(private val mAsyncTaskDao: ItemDao?) : AsyncTask<DataModel, Void, Void>() {

        override fun doInBackground(vararg params: DataModel): Void? {
            mAsyncTaskDao?.deleteAll()
            mAsyncTaskDao?.deleteDataAll()
            mAsyncTaskDao?.insertRows(params[0].rowsEntity!!)
            mAsyncTaskDao?.insertDataModel(params[0])
            return null
        }
    }
}