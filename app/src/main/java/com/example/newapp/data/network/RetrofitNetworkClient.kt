package com.example.newapp.data.network

import com.example.newapp.data.dto.BaseResponse
import com.example.newapp.data.dto.TracksSearchRequest
import com.example.newapp.data.dto.TracksSearchResponse
import com.example.newapp.domain.api.ITunesApiService
import com.example.newapp.domain.api.NetworkClient
import java.io.IOException

class RetrofitNetworkClient(private val api: ITunesApiService) : NetworkClient {

    override suspend fun doRequest(dto: Any): BaseResponse {
        return try {
            when (dto) {
                is TracksSearchRequest -> {
                    val response = api.searchTracks(query = dto.expression)
                    // iTunes API всегда возвращает 200 OK, даже если ничего не найдено
                    response.apply {
                        resultCode = if (resultCount > 0) 200 else 404
                    }
                }

                else -> BaseResponse().apply {
                    resultCode = 400
                    errorMessage = "Invalid request type"
                }
            }
        } catch (e: IOException) {
            BaseResponse().apply {
                resultCode = -1
                errorMessage = "Network error: ${e.message}"
            }
        } catch (e: Exception) {
            BaseResponse().apply {
                resultCode = -2
                errorMessage = "Unexpected error: ${e.message}"
            }
        }
    }
}