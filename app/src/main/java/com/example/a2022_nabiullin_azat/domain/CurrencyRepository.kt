package com.example.a2022_nabiullin_azat.domain

import com.example.a2022_nabiullin_azat.data.model.CurrencyResponse
import retrofit2.Response

interface CurrencyRepository {
    suspend fun getCurrencyList(date: String): Response<CurrencyResponse>
}