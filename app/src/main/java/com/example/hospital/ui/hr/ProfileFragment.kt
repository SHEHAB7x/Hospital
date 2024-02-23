package com.example.hospital.ui.hr

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.hospital.R
import com.example.hospital.data.Const
import com.example.hospital.data.ModelUser
import com.example.hospital.databinding.FragmentProfileBinding
import com.example.hospital.network.ResponseState
import com.example.hospital.utils.MySharedPreferences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var id: Int = 0
    private var from: String? = null
    private val viewModel: ProfileViewModel by viewModels()
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt(getString(R.string.id))!!
        from = arguments?.getString(getString(R.string.fromemplist))
        viewModel.getUserProfile(id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        onClicks()
        observers()
        return binding.root
    }


    private fun observers() {
        viewModel.profileLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseState.Success -> {
                    showUserData(state.data)
                    binding.loading.visibility = View.GONE
                }
                is ResponseState.Error -> showError(state.message)
                is ResponseState.Loading -> loading()
            }
        }
    }

    private fun loading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        binding.username.text = message
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        binding.loading.visibility = View.GONE
    }

    private fun showUserData(user: ModelUser) {
        binding.username.text = user.data.first_name + " " + user.data.last_name
        binding.gender.text = user.data.gender
        binding.phone.text = user.data.mobile
        binding.address.text = user.data.address
        binding.birthDate.text = user.data.birthday
        binding.specialist.text = "Specialist - " + user.data.specialist
        binding.email.text = user.data.email
        binding.status.text = user.data.status
    }

    private fun onClicks() {
        binding.btnBack.setOnClickListener {
            when(MySharedPreferences.getUserType()){
                Const.HR -> {
                    if(from == getString(R.string.fromemp))
                        Navigation.findNavController(it).navigate(R.id.action_profile_to_employeeList )
                    else
                        Navigation.findNavController(it).navigate(R.id.action_profile_to_HRHome)
                }
                Const.RECEPTIONIST -> Navigation.findNavController(it).navigate(R.id.action_profile_to_receptionFragment)
            }

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}