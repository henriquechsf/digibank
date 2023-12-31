package com.example.digitalbank.presenter.features.deposit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.digitalbank.data.enum.TransactionOperation
import com.example.digitalbank.data.enum.TransactionType
import com.example.digitalbank.data.model.Deposit
import com.example.digitalbank.data.model.Transaction
import com.example.digitalbank.databinding.FragmentDepositFormBinding
import com.example.digitalbank.utils.StateView
import com.example.digitalbank.utils.initToolbar
import com.example.digitalbank.utils.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.ZoneOffset

@AndroidEntryPoint
class DepositFormFragment : Fragment() {

    private var _binding: FragmentDepositFormBinding? = null
    private val binding get() = _binding!!

    private val depositViewModel: DepositViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDepositFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        initListeners()
    }

    private fun initListeners() = with(binding) {
        btnDeposit.setOnClickListener {
            validateDeposit()
        }
    }

    private fun validateDeposit() {
        val depositAmount = binding.edtDepositAmount.text.toString().trim()

        if (depositAmount.isNotEmpty()) {
            val deposit = Deposit(
                amount = depositAmount.toFloat(), date = System.currentTimeMillis()
            )
            saveDeposit(deposit)
        } else {
            showBottomSheet(message = "Digite um valor para deposito")
        }
    }

    private fun saveDeposit(deposit: Deposit) {
        depositViewModel.saveDeposit(deposit).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Sucess -> {
                    val transaction = Transaction(
                        id = stateView.data?.id ?: "",
                        operation = TransactionOperation.DEPOSIT,
                        date = stateView.data?.date ?: 0,
                        amount = stateView.data?.amount ?: 0F,
                        type = TransactionType.CASH_IN
                    )
                    saveTransaction(transaction)
                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = stateView.message)
                }
            }
        }
    }

    private fun saveTransaction(transaction: Transaction) {
        depositViewModel.saveTransaction(transaction).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                }
                is StateView.Sucess -> {
                    val action = DepositFormFragmentDirections
                        .actionDepositFormFragmentToDepositReceiptFragment(transaction)
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