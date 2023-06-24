package com.example.digitalbank.presenter.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.digitalbank.R
import com.example.digitalbank.databinding.FragmentLoginBinding
import com.example.digitalbank.utils.StateView
import com.example.digitalbank.utils.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        showBottomSheet(message = "Teste")
    }

    private fun initListeners() = with(binding) {
        btnLogin.setOnClickListener {
            validateData()
        }

        btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        btnForgot.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotAccountFragment)
        }
    }

    // TODO: refatorar validacao de campo
    private fun validateData() = with(binding) {
        val email = edtEmail.text.toString().trim()
        val password = edtPassword.text.toString().trim()

        if (email.isNotEmpty() || password.isNotEmpty()) {
            loginUser(email, password)
        } else {
            Toast.makeText(requireContext(), "Digite todos os campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser(email: String, password: String) {
        loginViewModel.login(email, password).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Sucess -> {
                    binding.progressBar.isVisible = false

                    findNavController().navigate(R.id.action_authentication_homeFragment)
                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false

                    Toast.makeText(
                        requireContext(),
                        stateView.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}