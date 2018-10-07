package com.kedar.ace.data

import com.kedar.ace.model.DataModel
import retrofit2.Call
import retrofit2.http.GET

/**
 * API endpoints
 */
interface ApiService {

    @GET("/s/2iodh4vg0eortkl/facts.json")
    fun getItems(): Call<DataModel>

}