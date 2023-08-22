package com.example.digitalbank.presenter.profile

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.digitalbank.R
import com.example.digitalbank.data.model.User
import com.example.digitalbank.databinding.FragmentProfileBinding
import com.example.digitalbank.utils.StateView
import com.example.digitalbank.utils.hideKeyboard
import com.example.digitalbank.utils.initToolbar
import com.example.digitalbank.utils.showBottomSheet
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()

    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        getProfile()
        initListeners()
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener {
            if (user != null) {
                validateData()
            }
        }
    }

    private fun getProfile() {
        profileViewModel.getProfile().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Sucess -> {
                    binding.progressBar.isVisible = false
                    stateView.data?.let { user = it }
                    configData()
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

    private fun validateData() = with(binding) {
        val name = edtName.text.toString().trim()
        val email = edtEmail.text.toString().trim()
        val phone = edtPhone.text.toString().trim()

        if (name.isNotEmpty() || email.isNotEmpty() || phone.isNotEmpty()) {
            hideKeyboard()

            val user = User(
                name = name,
                email = email,
                phone = phone,
            )
            saveProfile(user)
        } else {
            showBottomSheet(message = getString(R.string.text_name_empty))
        }
    }

    private fun saveProfile(user: User) {
        profileViewModel.saveProfile(user).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Sucess -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        "Perfil atualizado com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
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

    private fun configData() {
        binding.edtName.setText(user?.name)
        binding.edtPhone.setText(user?.phone)
        binding.edtEmail.setText(user?.email)
    }

    private fun checkPermissionCamera() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                Toast.makeText(requireContext(), "Permissao Aceita", Toast.LENGTH_SHORT).show()
                // Open camera
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(requireContext(), "Permissao Negada", Toast.LENGTH_SHORT).show()
            }
        }
        showDialogPermissionDenied(
            permissionlistener = permissionlistener,
            permission = Manifest.permission.CAMERA,
            message = R.string.text_message_gallery_denied_profile_fragment
        )
    }

    private fun checkPermissionGallery() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                Toast.makeText(requireContext(), "Permissao Aceita", Toast.LENGTH_SHORT).show()
                // Open camera
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(requireContext(), "Permissao Negada", Toast.LENGTH_SHORT).show()
            }
        }
        showDialogPermissionDenied(
            permissionlistener = permissionlistener,
            permission = Manifest.permission.READ_EXTERNAL_STORAGE,
            message = R.string.text_message_gallery_denied_profile_fragment
        )
    }

    private fun showDialogPermissionDenied(
        permissionlistener: PermissionListener,
        permission: String,
        message: Int
    ) {
        TedPermission.create()
            .setPermissionListener(permissionlistener)
            .setDeniedTitle("Permissao negada")
            .setDeniedMessage(message)
            .setDeniedCloseButtonText("Nao")
            .setGotoSettingButtonText("Sim")
            .setPermissions(permission)
            .check();
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}