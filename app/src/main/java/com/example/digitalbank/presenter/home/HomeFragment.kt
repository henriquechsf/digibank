package com.example.digitalbank.presenter.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.digitalbank.R
import com.example.digitalbank.data.model.Wallet
import com.example.digitalbank.databinding.FragmentHomeBinding
import com.example.digitalbank.utils.GetMask
import com.example.digitalbank.utils.StateView
import com.example.digitalbank.utils.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getWallet()
        initListeners()
    }

    private fun initListeners() = with(binding) {
        btnDeposit.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_depositFormFragment)
        }
    }

    private fun getWallet() {
        homeViewModel.getWallet().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }
                is StateView.Sucess -> {
                    stateView.data?.let {
                        showBalance(it.balance)
                    }
                }
                is StateView.Error -> {
                    showBottomSheet(message = stateView.message)
                }
            }
        }
    }

    private fun showBalance(balance: Float) {
        binding.tvBalance.text =
            getString(R.string.text_formatted_value, GetMask.getFormatedValue(balance))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}