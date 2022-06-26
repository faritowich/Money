package com.example.a2022_nabiullin_azat.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.a2022_nabiullin_azat.databinding.FragmentCurrencyDetailBinding
import com.example.a2022_nabiullin_azat.ui.BaseFragment
import com.example.a2022_nabiullin_azat.ui.viewmodels.DetailViewModel
import com.example.a2022_nabiullin_azat.ui.viewmodels.GenericCurrency

class CurrencyDetailFragment : BaseFragment<FragmentCurrencyDetailBinding>(
    FragmentCurrencyDetailBinding::inflate
) {
    private val viewModel: DetailViewModel by viewModels()
    private val args by navArgs<CurrencyDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.supportActionBar?.title = args.currentItem.charCode
        setCurrencyDescription()
        setInputListeners()
    }

    private fun setObserver() {
        viewModel.convertedAmount.observe(this) {
            when (it) {
                is GenericCurrency.Currency -> {
                    binding.textInputCurrency.setText(
                        it.value
                    )
                }
                is GenericCurrency.Rouble -> {
                    binding.textInputRouble.setText(
                        it.value
                    )
                }
            }
        }
    }

    private fun setInputListeners() {
        binding.textInputCurrency.addTextChangedListener {
            if (binding.textInputCurrency.isFocused) {
                viewModel.convertToRoubles(
                    binding.textInputCurrency.text.toString(),
                    args.currentItem.value
                )
            }
        }

        binding.textInputRouble.addTextChangedListener {
            if (binding.textInputRouble.isFocused) {
                viewModel.convertToCurrency(
                    binding.textInputRouble.text.toString(),
                    args.currentItem.value
                )
            }
        }
    }

    private fun setCurrencyDescription() {
        val exchangeRate = "%.2f ₽".format(args.currentItem.value, 2)
        binding.apply {
            textViewCurrencyTitle.text = args.currentItem.name
            textViewExchangeRate.text = exchangeRate
            inputLayoutCurrency.helperText = args.currentItem.charCode
            inputLayoutRouble.helperText = "RUB"
        }
    }
}