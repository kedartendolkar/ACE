package com.kedar.ace.data.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.kedar.ace.data.local.entity.DataModel
import com.kedar.ace.data.local.entity.RowsEntity


@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRows(rowsEntity: List<RowsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDataModel(dataModel: DataModel)

    @Query("DELETE FROM rows_table")
    fun deleteAll()

    @Query("DELETE FROM data_table")
    fun deleteDataAll()

    @Query("SELECT * FROM rows_table")
    fun getAllItems(): LiveData<List<RowsEntity>>

    @Query("SELECT * FROM data_table")
    fun getDataModel(): LiveData<DataModel>
}