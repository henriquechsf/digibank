package com.example.digitalbank.presenter.auth.register

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
import com.example.digitalbank.data.model.User
import com.example.digitalbank.databinding.FragmentRegisterBinding
import com.example.digitalbank.presenter.profile.ProfileViewModel
import com.example.digitalbank.utils.FirebaseHelper
import com.example.digitalbank.utils.StateView
import com.example.digitalbank.utils.initToolbar
import com.example.digitalbank.utils.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        initListeners()
    }

    private fun initListeners() {
        binding.btnRegister.setOnClickListener {
            validateData()
        }
    }

    // TODO: refatorar validacao de campo
    private fun validateData() = with(binding) {
        val name = edtName.text.toString().trim()
        val email = edtEmail.text.toString().trim()
        val phone = edtPhone.text.toString().trim()
        val password = edtPassword.text.toString().trim()
        val confirmPassword = edtConfirmPassword.text.toString().trim()

        if (name.isNotEmpty() || email.isNotEmpty() || phone.isNotEmpty() || password.isNotEmpty() || confirmPassword.isNotEmpty()) {
            if (password == confirmPassword) {
                val user = User(
                    name = name,
                    email = email,
                    phone = phone,
                    password = password
                )
                registerUser(user)
            } else {
                showBottomSheet(message = "Senhas nao conferem")
            }
        } else {
            showBottomSheet(message = getString(R.string.text_name_empty))
        }
    }

    private fun registerUser(user: User) {
        registerViewModel.register(user).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Sucess -> {
                    saveProfile(user.copy(id = FirebaseHelper.getUserId()))

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

    private fun saveProfile(user: User) {
        profileViewModel.saveProfile(user).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {}
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