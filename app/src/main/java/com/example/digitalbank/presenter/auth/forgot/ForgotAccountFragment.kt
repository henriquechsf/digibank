package com.example.digitalbank.presenter.auth.forgot

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
import com.example.digitalbank.databinding.FragmentForgotAccountBinding
import com.example.digitalbank.utils.StateView
import com.example.digitalbank.utils.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotAccountFragment : Fragment() {

    private var _binding: FragmentForgotAccountBinding? = null
    private val binding get() = _binding!!

    private val forgotViewModel: ForgotViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentForgotAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        initListeners()
    }

    private fun initListeners() {
        binding.btnSendMail.setOnClickListener {
            validateData()
        }
    }

    // TODO: refatorar validacao de campo
    private fun validateData() = with(binding) {
        val email = edtEmail.text.toString().trim()

        if (email.isNotEmpty()) {
            forgotAccount(email)
        } else {
            Toast.makeText(requireContext(), "Digite todos os campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun forgotAccount(email: String) {
        forgotViewModel.forgot(email).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Sucess -> {
                    binding.progressBar.isVisible = false

                    findNavController().popBackStack()

                    Toast.makeText(
                        requireContext(),
                        "Email enviado com sucesso.",
                        Toast.LENGTH_SHORT
                    ).show()
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