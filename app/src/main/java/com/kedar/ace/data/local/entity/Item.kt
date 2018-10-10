package com.kedar.ace.data.local.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "item_table")
data class Item(@PrimaryKey @ColumnInfo(name = "title") val title: String, @ColumnInfo(name = "description") val description: String, @ColumnInfo(name = "imageUrl") val imageUrl: String)