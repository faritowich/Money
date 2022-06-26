package com.example.a2022_nabiullin_azat.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2022_nabiullin_azat.data.model.CurrencyResponse
import com.example.a2022_nabiullin_azat.domain.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

enum class CurrencyApiStatus { LOADING, ERROR, DONE, NULL }

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val repository: CurrencyRepository) : ViewModel() {

    private val _currencyResponse = MutableLiveData<CurrencyResponse?>()
    val currencyResponse: LiveData<CurrencyResponse?> = _currencyResponse

    private val _status = MutableLiveData<CurrencyApiStatus>()
    val status: LiveData<CurrencyApiStatus> = _status

    init {
        Log.d("debugging", "VM created")
    }

    fun getCurrencyList(date: String){
        viewModelScope.launch {
            _status.value = CurrencyApiStatus.LOADING
            try {
                val response: Response<CurrencyResponse> = repository.getCurrencyList(date)
                if (response.body() != null) {
                    _currencyResponse.value = response.body()
                    _status.value = CurrencyApiStatus.DONE
                } else {
                    _currencyResponse.value = null
                    _status.value = CurrencyApiStatus.NULL
                }
            } catch (e: Exception) {
                _status.value = CurrencyApiStatus.ERROR
            }
        }
    }
}