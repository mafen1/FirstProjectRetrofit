package com.example.myanime.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val baseUrl = "https://anime-facts-rest-api.herokuapp.com"
    private var retrofit: Retrofit? = null

    private fun getClient(baseUrl: String): Retrofit{
        if (retrofit == null){
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        }
        return retrofit!!
    }

    fun getResponse() = getClient(baseUrl).create(RequestGet::class.java)

}