package com.example.digitalbank.presenter.features.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.digitalbank.R
import com.example.digitalbank.data.model.User
import com.example.digitalbank.databinding.FragmentTransferUserListBinding
import com.example.digitalbank.presenter.features.transfer.adapter.TransferUserAdapter
import com.example.digitalbank.utils.StateView
import com.example.digitalbank.utils.initToolbar
import com.example.digitalbank.utils.showBottomSheet
import com.ferfalk.simplesearchview.SimpleSearchView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay


@AndroidEntryPoint
class TransferUserListFragment : Fragment() {

    private var _binding: FragmentTransferUserListBinding? = null
    private val binding get() = _binding!!

    private val transferUserListViewModel: TransferUserListViewModel by viewModels()

    private lateinit var transferUserAdapter: TransferUserAdapter

    private var profilesList = listOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        configSearchView()
    }

    private fun initRecyclerView() {
        transferUserAdapter = TransferUserAdapter { userSelected ->
            Toast.makeText(requireContext(), userSelected.name, Toast.LENGTH_SHORT).show()
        }

        with(binding.rvUsers) {
            setHasFixedSize(true)
            adapter = transferUserAdapter
        }
    }

    private fun getProfileList() {
        transferUserListViewModel.getProfileList().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }
                is StateView.Sucess -> {
                    profilesList = stateView.data ?: emptyList()
                    transferUserAdapter.submitList(profilesList)
                    binding.progressBar.isVisible = false
                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = stateView.message)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val item = menu.findItem(R.id.action_search)
        binding.searchView.setMenuItem(item)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun configSearchView() = with(binding) {
        searchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return if (newText.isNotEmpty()) {
                    val newList = profilesList.filter { user ->
                        user.name.contains(newText, true)
                    }
                    transferUserAdapter.submitList(newList)
                    true
                } else {
                    transferUserAdapter.submitList(profilesList)
                    false
                }
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                return false
            }
        })



        searchView.setOnSearchViewListener(object : SimpleSearchView.SearchViewListener {
            override fun onSearchViewClosed() {
                transferUserAdapter.submitList(profilesList)
            }

            override fun onSearchViewClosedAnimation() {}
            override fun onSearchViewShown() {}
            override fun onSearchViewShownAnimation() {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}