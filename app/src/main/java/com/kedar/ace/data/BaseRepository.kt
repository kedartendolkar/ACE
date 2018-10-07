package com.kedar.ace.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kedar.ace.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


abstract class BaseRepository {

    companion object {
        lateinit var apiService: ApiService
    }

    init {
        apiService = getRetrofit().create(ApiService::class.java)
    }

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build()
    }

    private fun getGson(): Gson {

        return GsonBuilder()
                .setLenient()
                .create()
    }

}