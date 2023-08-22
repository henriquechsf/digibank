package com.example.digitalbank.presenter.features.recharge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.digitalbank.data.model.Recharge
import com.example.digitalbank.databinding.FragmentRechargeReceiptBinding
import com.example.digitalbank.utils.GetMask
import com.example.digitalbank.utils.StateView
import com.example.digitalbank.utils.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RechargeReceiptFragment : Fragment() {

    private var _binding: FragmentRechargeReceiptBinding? = null
    private val binding get() = _binding!!

    private val args: RechargeReceiptFragmentArgs by navArgs()

    private val rechargeViewModel: RechargeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRechargeReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar, homeAsUpEnabled = false)

        getRecharge()
        initListeners()
    }

    private fun initListeners() {
        binding.btnOk.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getRecharge() {
        rechargeViewModel.getRecharge(args.rechargeId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }
                is StateView.Sucess -> {
                    stateView.data?.let { configData(it) }
                }
                is StateView.Error -> {
                    Toast.makeText(requireContext(), "Ocorreu um erro", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun configData(recharge: Recharge) = with(binding) {
        tvTransactionCode.text = recharge.id
        tvTransactionDate.text =
            GetMask.getFormatedDate(recharge.date, GetMask.DAY_MONTH_YEAR_HOUR_MINUTE)
        tvAmount.text = GetMask.getFormatedValue(recharge.amount)
        tvPhone.text = recharge.phoneNumber
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}