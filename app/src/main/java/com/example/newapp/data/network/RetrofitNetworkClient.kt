package com.example.newapp.data.network

import androidx.tracing.perfetto.handshake.protocol.Response
import com.example.newapp.creator.Storage
import com.example.newapp.domain.api.NetworkClient
import com.example.newapp.data.dto.BaseResponse
import com.example.newapp.data.dto.TracksSearchRequest
import com.example.newapp.data.dto.TracksSearchResponse


class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {

    override fun doRequest(dto: Any): BaseResponse {           // ← имя параметра должно быть dto
        val request = dto as TracksSearchRequest               // делаем безопасный каст
        val searchList = storage.search(request.expression)

        return TracksSearchResponse(searchList).apply {
            resultCode = 200
        }
    }
}