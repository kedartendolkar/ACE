package com.kedar.ace.data.local.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * API and Room model for Items API
 */
@Entity(tableName = "data_table")
data class DataModel(
        @SerializedName("title")
        @PrimaryKey
        @ColumnInfo(name = "title")
        var title: String = "",

        @Ignore
        @SerializedName("rows")
        var rowsEntity: List<RowsEntity>? = null,

        @Ignore
        var error: Error? = null
)

@Entity(tableName = "rows_table")
data class RowsEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Int,

        var titleId: Int,

        @SerializedName("title")
        @ColumnInfo(name = "title")
        var title: String?,

        @SerializedName("description")
        @ColumnInfo(name = "description")
        var description: String?,

        @SerializedName("imageHref")
        @ColumnInfo(name = "imageHref")
        var imagehref: String?)

data class Error(val errorCode: Int)