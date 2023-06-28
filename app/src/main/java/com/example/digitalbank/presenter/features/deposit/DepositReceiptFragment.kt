package com.example.digitalbank.presenter.features.deposit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.digitalbank.data.model.Transaction
import com.example.digitalbank.databinding.FragmentDepositReceiptBinding
import com.example.digitalbank.utils.GetMask
import com.example.digitalbank.utils.initToolbar

class DepositReceiptFragment : Fragment() {

    private var _binding: FragmentDepositReceiptBinding? = null
    private val binding get() = _binding!!

    private val args: DepositReceiptFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDepositReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        configData(args.transaction)
        initListeners()
    }

    private fun configData(transaction: Transaction) = with(binding) {
        tvTransactionCode.text = transaction.id
        tvTransactionDate.text =
            GetMask.getFormatedDate(transaction.date, GetMask.DAY_MONTH_YEAR_HOUR_MINUTE)
        tvAmount.text = GetMask.getFormatedValue(transaction.amount)
    }

    private fun initListeners() {
        binding.btnOk.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}