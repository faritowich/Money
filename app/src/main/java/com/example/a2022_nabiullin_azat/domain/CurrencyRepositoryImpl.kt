package com.example.a2022_nabiullin_azat.domain

import com.example.a2022_nabiullin_azat.data.model.CurrencyResponse
import com.example.a2022_nabiullin_azat.data.network.CurrencyApi
import retrofit2.Response
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val api: CurrencyApi) : CurrencyRepository {
    override suspend fun getCurrencyList(date: String): Response<CurrencyResponse> {
        return api.getCurrencyList(date)
    }
}