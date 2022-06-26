package com.example.a2022_nabiullin_azat.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class GenericCurrency(val value: String) {
    class Rouble(value: String) : GenericCurrency(value)
    class Currency(value: String) : GenericCurrency(value)
}

class DetailViewModel : ViewModel() {

    private val _convertedAmount = MutableLiveData<GenericCurrency>()
    val convertedAmount: LiveData<GenericCurrency> = _convertedAmount

    fun convertToRoubles(input: String, exchangeRate: Double) {
        _convertedAmount.value = if (!input.isEmpty()) {
            val formattedAmount = "%.2f".format(input.toDouble() * exchangeRate, 2).toDouble()
            GenericCurrency.Rouble(formattedAmount.toString())
        } else GenericCurrency.Rouble("")
    }

    fun convertToCurrency(input: String, exchangeRate: Double) {
        _convertedAmount.value = if (!input.isEmpty()) {
            val formattedAmount = "%.2f".format(input.toDouble() / exchangeRate, 2).toDouble()
            GenericCurrency.Currency(formattedAmount.toString())
        } else GenericCurrency.Currency("")
    }
}