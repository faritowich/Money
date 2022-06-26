package com.example.a2022_nabiullin_azat.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a2022_nabiullin_azat.data.model.Currency
import com.example.a2022_nabiullin_azat.databinding.CurrencyItemBinding

class CurrencyListAdapter : ListAdapter<Currency, CurrencyListAdapter.CurrencyListViewHolder>(
    UserItemDiffCallback()
) {

    class CurrencyListViewHolder(
        private val binding: CurrencyItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currency: Currency) {
            val exchangeRate = "%.2f ₽".format(currency.value, 2)
            binding.apply {
                currencyNameTextView.text = currency.charCode
                exchangeRateTextView.text = exchangeRate
            }
        }
    }

    class UserItemDiffCallback : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CurrencyListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CurrencyListViewHolder(
            CurrencyItemBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            val action = CurrencyListFragmentDirections.actionCurrencyListFragmentToCurrencyDetailFragment(item)

            holder.itemView.findNavController().navigate(action)
        }
    }
}