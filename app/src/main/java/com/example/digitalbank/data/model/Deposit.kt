package com.example.digitalbank.data.model

import com.google.firebase.database.FirebaseDatabase

data class Deposit(
    var id: String = "",
    var date: Long = 0,
    val amount: Float = 0F
) {
    init {
        this.id = FirebaseDatabase.getInstance().reference.push().key ?: ""
    }
}
