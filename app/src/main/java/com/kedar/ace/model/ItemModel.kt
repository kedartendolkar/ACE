package com.kedar.ace.model

import com.google.gson.annotations.SerializedName

/**
 * API model for Items API
 */
data class DataModel(
        @SerializedName("title")
        val mTitle: String? = null,
        @SerializedName("rows")
        val mRowsEntity: List<RowsEntity>? = null
)

data class RowsEntity(
        @SerializedName("title")
        val mTitle: String,
        @SerializedName("description")
        val mDescription: String,
        @SerializedName("imageHref")
        val mImagehref: String)