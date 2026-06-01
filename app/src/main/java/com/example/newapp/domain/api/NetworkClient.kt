package com.example.newapp.domain.api

import com.example.newapp.data.dto.BaseResponse

interface NetworkClient {
    suspend fun doRequest(dto: Any): BaseResponse
}