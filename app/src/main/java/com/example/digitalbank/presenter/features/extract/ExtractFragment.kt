package com.example.digitalbank.presenter.features.extract

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digitalbank.data.enum.TransactionOperation
import com.example.digitalbank.databinding.FragmentExtractBinding
import com.example.digitalbank.presenter.home.HomeFragmentDirections
import com.example.digitalbank.presenter.home.TransactionsAdapter
import com.example.digitalbank.utils.StateView
import com.example.digitalbank.utils.initToolbar
import com.example.digitalbank.utils.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExtractFragment : Fragment() {

    private var _binding: FragmentExtractBinding? = null
    private val binding get() = _binding!!

    private val extractViewModel: ExtractViewModel by viewModels()

    private lateinit var adapterTransactions: TransactionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExtractBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        initRecyclerTransactions()
        getTransactions()
    }

    private fun initRecyclerTransactions() {
        adapterTransactions = TransactionsAdapter(requireContext()) { selectedTransaction ->
            when (selectedTransaction.operation) {
                TransactionOperation.DEPOSIT -> {
                    val action = ExtractFragmentDirections.actionExtractFragmentToDepositReceiptFragment(
                        selectedTransaction
                    )
                    findNavController().navigate(action)
                }
                TransactionOperation.RECHARGE -> {
                    val action = ExtractFragmentDirections.actionExtractFragmentToRechargeReceiptFragment(
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
        extractViewModel.getTransactions().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Sucess -> {
                    binding.progressBar.isVisible = false
                    adapterTransactions.submitList(stateView.data?.reversed()?.take(6))
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