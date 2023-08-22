package com.example.digitalbank.presenter.features.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.digitalbank.databinding.FragmentTransferUserListBinding
import com.example.digitalbank.presenter.features.transfer.adapter.TransferUserAdapter
import com.example.digitalbank.utils.StateView
import com.example.digitalbank.utils.initToolbar
import com.example.digitalbank.utils.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferUserListFragment : Fragment() {

    private var _binding: FragmentTransferUserListBinding? = null
    private val binding get() = _binding!!

    private val transferUserListViewModel: TransferUserListViewModel by viewModels()

    private lateinit var transferUserAdapter: TransferUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransferUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        initRecyclerView()
        getProfileList()
    }

    private fun initRecyclerView() {
        transferUserAdapter = TransferUserAdapter {userSelected ->
            Toast.makeText(requireContext(), userSelected.name, Toast.LENGTH_SHORT).show()
        }

        with(binding.rvUsers) {
            setHasFixedSize(true)
            adapter = transferUserAdapter
        }
    }

    private fun getProfileList() {
        transferUserListViewModel.getProfileList().observe(viewLifecycleOwner) {stateView ->
            when(stateView) {
                is StateView.Loading -> {

                }
                is StateView.Sucess -> {
                    transferUserAdapter.submitList(stateView.data)
                    binding.progressBar.isVisible = false
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