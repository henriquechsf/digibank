package com.example.digitalbank.data.model

import com.google.firebase.database.FirebaseDatabase

data class Transaction(
    var id: String = "",
    val description: String = "",
    val date: Long = 0,
    val value: Float = 0F
) {
    init {
        this.id = FirebaseDatabase.getInstance().reference.push().key ?: ""
    }
}
