package com.example.myanime.domain.useCase

import com.example.myanime.data.api.RequestGet
import com.example.myanime.domain.repository.Repository

class UseCase(private val repositoryImpl: RequestGet.RequestGetImpl) {

    suspend fun client(json: Boolean) = repositoryImpl.getRequest(json)

}