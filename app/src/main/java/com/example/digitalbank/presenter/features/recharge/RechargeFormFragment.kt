package com.example.digitalbank.presenter.features.recharge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.digitalbank.data.enum.TransactionOperation
import com.example.digitalbank.data.enum.TransactionType
import com.example.digitalbank.data.model.Recharge
import com.example.digitalbank.data.model.Transaction
import com.example.digitalbank.databinding.FragmentRechargeFormBinding
import com.example.digitalbank.utils.StateView
import com.example.digitalbank.utils.hideKeyboard
import com.example.digitalbank.utils.initToolbar
import com.example.digitalbank.utils.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RechargeFormFragment : Fragment() {

    private var _binding: FragmentRechargeFormBinding? = null
    private val binding get() = _binding!!

    private val rechargeViewModel: RechargeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRechargeFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        initListeners()
    }

    private fun initListeners() = with(binding) {
        btnRecharge.setOnClickListener {
            validateRecharge()
        }
    }

    private fun validateRecharge() {
        val rechargeAmount = binding.edtRechargeAmount.text.toString().trim()
        val rechargePhoneNumber = binding.edtRechargePhoneNumber.text.toString().trim()

        if (rechargeAmount.isNotEmpty() && rechargePhoneNumber.isNotEmpty()) {
            hideKeyboard()

            val recharge = Recharge(
                amount = rechargeAmount.toFloat(),
                phoneNumber = rechargePhoneNumber,
                date = System.currentTimeMillis()
            )
            saveRecharge(recharge)
        } else {
            showBottomSheet(message = "Digite um valor para recarga")
        }
    }

    private fun saveRecharge(recharge: Recharge) {
        rechargeViewModel.saveRecharge(recharge).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Sucess -> {
                    saveTransaction(recharge)
                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = stateView.message)
                }
            }
        }
    }

    private fun saveTransaction(recharge: Recharge) {
        val transaction = Transaction(
            id = recharge.id,
            operation = TransactionOperation.RECHARGE,
            date = recharge.date,
            amount = recharge.amount,
            type = TransactionType.CASH_OUT
        )

        rechargeViewModel.saveTransaction(transaction).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                }
                is StateView.Sucess -> {
                    val action =
                        RechargeFormFragmentDirections.actionRechargeFormFragmentToRechargeReceiptFragment(
                            recharge.id
                        )
                    findNavController().navigate(action)
                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = stateView.message)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}