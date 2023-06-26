package com.example.digitalbank.data.enum

import androidx.annotation.ColorRes
import com.example.digitalbank.R

enum class TransactionOperation(
    val description: String,
    val character: Char,
    @ColorRes val color: Int
) {
    DEPOSIT("Deposito", 'D', R.color.green_500)
}