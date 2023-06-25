package com.example.digitalbank.utils

import com.example.digitalbank.R
import com.google.firebase.auth.FirebaseAuth

// TODO: Mover implementacao para classe repository
class FirebaseHelper {

    companion object {
        fun isAuthenticated(): Boolean = FirebaseAuth.getInstance().currentUser != null

        fun getUserId() = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // TODO: Adicionar demais mapeamentos de erro Firebase
        fun validError(error: String): Int {
            return when {
                error.contains("There is no user record corresponding to this identifier.") -> {
                    R.string.account_not_registered_register_fragment
                }
                error.contains("The email address is badly formatted.") -> {
                    R.string.invalid_email_register_fragment
                }
                error.contains("The password is invalid or the user does not have a password.") -> {
                    R.string.invalid_password_register_fragment
                }
                else -> R.string.error_generic
            }
        }
    }
}