package com.kedar.ace.data.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.kedar.ace.data.local.entity.Item


@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Item)

    @Query("DELETE FROM item_table")
    fun deleteAll()

    @Query("SELECT * FROM item_table")
    fun getAllItems(): LiveData<List<Item>>
}