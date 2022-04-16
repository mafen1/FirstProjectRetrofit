package com.example.myanime.core

sealed class ApiState {
    object ApiEmpty : ApiState()
    object ApiLoading: ApiState()
    object Success: ApiState()
    class Error(message: String): ApiState()
}
