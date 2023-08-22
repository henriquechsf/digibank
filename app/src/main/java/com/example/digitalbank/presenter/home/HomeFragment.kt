package com.example.digitalbank.presenter.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digitalbank.R
import com.example.digitalbank.data.enum.TransactionOperation
import com.example.digitalbank.data.enum.TransactionType
import com.example.digitalbank.data.model.Transaction
import com.example.digitalbank.databinding.FragmentHomeBinding
import com.example.digitalbank.utils.FirebaseHelper
import com.example.digitalbank.utils.GetMask
import com.example.digitalbank.utils.StateView
import com.example.digitalbank.utils.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var adapterTransactions: TransactionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerTransactions()
        getTransactions()
        initListeners()
    }

    private fun initListeners() = with(binding) {
        btnDeposit.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_depositFormFragment)
        }
        btnShowAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_extractFragment)
        }
        btnHistory.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_extractFragment)
        }
        btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        btnTransfer.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_transferUserListFragment)
        }
        btnLogout.setOnClickListener {
            FirebaseHelper.getAuth().signOut()
            findNavController().navigate(R.id.action_homeFragment_to_authentication)
        }
        btnCardLogout.setOnClickListener {
            FirebaseHelper.getAuth().signOut()
            findNavController().navigate(R.id.action_homeFragment_to_authentication)
        }
        btnCardRecharge.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_rechargeFormFragment)
        }
    }

    private fun initRecyclerTransactions() {
        adapterTransactions = TransactionsAdapter(requireContext()) { selectedTransaction ->
            when (selectedTransaction.operation) {
                TransactionOperation.DEPOSIT -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToDepositReceiptFragment(
                        selectedTransaction
                    )
                    findNavController().navigate(action)
                }
                TransactionOperation.RECHARGE -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToRechargeReceiptFragment(
                        selectedTransaction.id
                    )
                    findNavController().navigate(action)
                }
                else -> {}
            }
        }

        with(binding.rvTransactions) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterTransactions
        }
    }

    private fun getTransactions() {
        homeViewModel.getTransactions().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Sucess -> {
                    binding.progressBar.isVisible = false
                    adapterTransactions.submitList(stateView.data?.reversed()?.take(6))
                    showBalance(stateView.data ?: emptyList())
                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = stateView.message)
                }
            }
        }
    }

    private fun showBalance(transactions: List<Transaction>) {
        var cashIn = 0F
        var cashOut = 0F

        transactions.forEach { transaction ->
            if (transaction.type == TransactionType.CASH_IN) {
                cashIn += transaction.amount
            } else {
                cashOut += transaction.amount
            }
        }

        binding.tvBalance.text =
            getString(R.string.text_formatted_value, GetMask.getFormatedValue(cashIn - cashOut))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}