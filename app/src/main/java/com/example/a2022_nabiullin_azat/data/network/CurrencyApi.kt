package com.example.a2022_nabiullin_azat.data.network

import com.example.a2022_nabiullin_azat.data.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {
    @GET("archive/{date}.js")
    suspend fun getCurrencyList(@Path("date") date: String): Response<CurrencyResponse>
}