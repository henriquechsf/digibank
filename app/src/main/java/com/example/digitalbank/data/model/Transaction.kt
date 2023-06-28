package com.example.digitalbank.data.model

import android.os.Parcelable
import com.example.digitalbank.data.enum.TransactionOperation
import com.example.digitalbank.data.enum.TransactionType
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    var id: String = "",
    var operation: TransactionOperation? = null,
    var date: Long = 0,
    var amount: Float = 0F,
    var type: TransactionType? = null
) : Parcelable
