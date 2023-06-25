package com.example.digitalbank.presenter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalbank.data.model.Transaction
import com.example.digitalbank.databinding.LastTransactionItemBinding
import com.example.digitalbank.utils.GetMask

class LastTransactionsAdapter(
    private val transactionSelected: (Transaction) -> Unit
) : ListAdapter<Transaction, LastTransactionsAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areItemsTheSame(
                oldItem: Transaction,
                newItem: Transaction
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Transaction,
                newItem: Transaction
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LastTransactionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = getItem(position)

        holder.binding.tvTransactionDescription.text = transaction.description
        holder.binding.tvTransactionType.text = when (transaction.description) {
            "TRANSFERENCIA" -> "T"
            "DEPOSITO" -> "D"
            "RECARGA" -> "R"
            else -> ""
        }
        holder.binding.tvTransactionValue.text = GetMask.getFormatedValue(transaction.value)
        holder.binding.tvTransactionDate.text =
            GetMask.getFormatedDate(transaction.date, GetMask.DAY_MONTH_YEAR_HOUR_MINUTE)
    }

    inner class ViewHolder(val binding: LastTransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}