package com.kedar.ace.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.kedar.ace.data.local.dao.ItemDao
import com.kedar.ace.data.local.entity.Item


@Database(entities = [(Item::class)], version = 1, exportSchema = false)
abstract class ItemRoomDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        private var INSTANCE: ItemRoomDatabase? = null

        fun getInstance(context: Context): ItemRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(ItemRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder<ItemRoomDatabase>(context.applicationContext, ItemRoomDatabase::class.java, "item_database")
                                .build()
                    }
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }


}