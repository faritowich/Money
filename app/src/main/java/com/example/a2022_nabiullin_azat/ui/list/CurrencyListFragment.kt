package com.example.a2022_nabiullin_azat.ui.list

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a2022_nabiullin_azat.R
import com.example.a2022_nabiullin_azat.databinding.FragmentCurrencyListBinding
import com.example.a2022_nabiullin_azat.ui.BaseFragment
import com.example.a2022_nabiullin_azat.ui.viewmodels.CurrencyApiStatus
import com.example.a2022_nabiullin_azat.ui.viewmodels.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CurrencyListFragment : BaseFragment<FragmentCurrencyListBinding>(
    FragmentCurrencyListBinding::inflate
) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CurrencyListAdapter
    private val viewModel: CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        val calendar = Calendar.getInstance()
        binding.dateInputEditText.hint = handleDate(calendar)

        viewModel.getCurrencyList(handleDate(calendar))

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            binding.dateInputEditText.hint = handleDate(calendar)
            viewModel.getCurrencyList(handleDate(calendar))
        }

        binding.dateInputEditText.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                R.style.DatePickerDialogTheme,
                datePicker,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.maxDate = Date().time
            datePickerDialog.show()
        }
    }

    private fun handleDate(calendar: Calendar): String {
        val format = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val chosenDate = sdf.format(calendar.time)
        return chosenDate
    }

    private fun setRecyclerView() {
        adapter = CurrencyListAdapter()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = adapter
    }

    private fun setObservers(){
        viewModel.currencyResponse.observe(this, { response ->
            adapter.submitList(response?.currency?.values?.toList())
        })

        viewModel.status.observe(this) {
            when (it) {
                CurrencyApiStatus.LOADING -> {
                    binding.apply {
                        textviewNoCurrencyData.visibility = View.GONE
                        loadingStatusImageView.visibility = View.VISIBLE
                        loadingStatusImageView.setImageResource(R.drawable.loading_animation)
                    }
                }
                CurrencyApiStatus.ERROR -> {
                    binding.apply {
                        textviewNoCurrencyData.visibility = View.GONE
                        loadingStatusImageView.visibility = View.VISIBLE
                        loadingStatusImageView.setImageResource(R.drawable.ic_connection_error)
                    }
                }
                CurrencyApiStatus.DONE -> {
                    binding.apply {
                        textviewNoCurrencyData.visibility = View.GONE
                        loadingStatusImageView.visibility = View.GONE
                    }
                }
                CurrencyApiStatus.NULL -> {
                    binding.apply {
                        textviewNoCurrencyData.visibility = View.VISIBLE
                        loadingStatusImageView.visibility = View.GONE
                    }
                }
            }
        }
    }
}