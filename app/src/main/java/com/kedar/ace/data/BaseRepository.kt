package com.kedar.ace.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kedar.ace.data.remote.ApiService
import com.kedar.ace.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


abstract class BaseRepository {

    companion object {
        lateinit var apiService: ApiService
    }

    init {
        apiService = getRetrofit().create(ApiService::class.java)
    }

    /**
     * Returns the Retrofit client with the base URL and converter factory
     */
    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build()
    }

    /**
     * Returns the custom Gson instance for converter factory
     */
    private fun getGson(): Gson {

        return GsonBuilder()
                .setLenient()
                .create()
    }

}