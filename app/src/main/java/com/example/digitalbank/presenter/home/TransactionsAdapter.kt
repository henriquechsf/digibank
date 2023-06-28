package com.example.digitalbank.presenter.home

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalbank.R
import com.example.digitalbank.data.model.Transaction
import com.example.digitalbank.databinding.TransactionItemBinding
import com.example.digitalbank.utils.GetMask

class TransactionsAdapter(
    private val context: Context,
    private val transactionSelected: (Transaction) -> Unit
) : ListAdapter<Transaction, TransactionsAdapter.ViewHolder>(DIFF_CALLBACK) {

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
            TransactionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = getItem(position)

        holder.binding.tvTransactionDescription.text = transaction.operation?.description
        holder.binding.tvTransactionType.text = transaction.operation?.character.toString()
        holder.binding.tvTransactionType.backgroundTintList =
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    transaction.operation?.color ?: R.color.blue_500
                )
            )
        holder.binding.tvTransactionValue.text = context.getString(
            R.string.text_formatted_value,
            GetMask.getFormatedValue(transaction.amount)
        )
        holder.binding.tvTransactionDate.text =
            GetMask.getFormatedDate(transaction.date, GetMask.DAY_MONTH_YEAR_HOUR_MINUTE)

        holder.itemView.setOnClickListener {
            transactionSelected(transaction)
        }
    }

    inner class ViewHolder(val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}