package com.kedar.ace.data.remote

import com.kedar.ace.data.local.entity.DataModel
import retrofit2.Call
import retrofit2.http.GET

/**
 * API endpoints
 */
interface ApiService {

    @GET("/s/2iodh4vg0eortkl/facts.json")
    fun getItems(): Call<DataModel>

}