package com.example.myanime.domain.repository

import com.example.myanime.data.api.RequestGet
import com.example.myanime.data.models.ResponseAnime
import retrofit2.Response

interface Repository {

    suspend fun getJson(json: Boolean): Response<ResponseAnime>

    class RepositoryImpl(json: Boolean): Repository{
        override suspend fun getJson(json: Boolean): Response<ResponseAnime> {
            return RequestGet.RequestGetImpl().getRequest(json)
        }

    }

}