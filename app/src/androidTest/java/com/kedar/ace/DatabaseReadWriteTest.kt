package com.kedar.ace

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.kedar.ace.data.local.ItemRoomDatabase
import com.kedar.ace.data.local.dao.ItemDao
import com.kedar.ace.data.local.entity.DataModel
import com.kedar.ace.data.local.entity.RowsEntity
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class DatabaseReadWriteTest {
    private var itemDao: ItemDao? = null
    private var mDb: ItemRoomDatabase? = null

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        mDb = Room.inMemoryDatabaseBuilder(context, ItemRoomDatabase::class.java).build()
        itemDao = mDb!!.itemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDb!!.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeItemAndReadTest() {
        val dataModel = DataModel(title = "Test title")
        itemDao!!.insertDataModel(dataModel)
        val model = LiveDataTestUtil.getValue(itemDao!!.getDataModel())
        assertThat(model.title, equalTo(dataModel.title))
    }

    @Test
    @Throws(Exception::class)
    fun writeRowEntityAndReadTest() {
        val dummyList = ArrayList<RowsEntity>()
        dummyList.add(RowsEntity(1, 11, "Dummy item 1", "Dummy Description 1", null))
        dummyList.add(RowsEntity(2, 22, "Dummy item 2", "Dummy Description 2", null))
        dummyList.add(RowsEntity(3, 33, "Dummy item 3", "Dummy Description 3", null))
        itemDao!!.insertRows(dummyList)
        val byName = LiveDataTestUtil.getValue(itemDao!!.getAllItems())
        assertThat(byName.size, equalTo(dummyList.size))
        assertThat(byName[0].title, equalTo(dummyList[0].title))
    }
}