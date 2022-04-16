package com.example.myanime.data.api

import com.example.myanime.data.models.ResponseAnime
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestGet {
    @GET("/api/v1")
    suspend fun getRequest(@Query("json") json: Boolean): Response<ResponseAnime>

    class RequestGetImpl(): RequestGet{
        override suspend fun getRequest(json: Boolean): Response<ResponseAnime> {
            return ApiClient.getResponse().getRequest(json)
        }

    }
}