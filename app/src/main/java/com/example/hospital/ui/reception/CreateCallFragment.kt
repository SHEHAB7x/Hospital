package com.example.hospital.ui.reception

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.hospital.R
import com.example.hospital.data.Const
import com.example.hospital.databinding.FragmentCreateCallBinding
import com.example.hospital.network.ResponseState


class CreateCallFragment : Fragment() {
    private var _binding: FragmentCreateCallBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CreateCallViewModel by viewModels()
    private var doctorId: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateCallBinding.inflate(inflater, container, false)
        noClicks()
        observers()
        return binding.root
    }

    private fun observers() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(Const.DOCTOR_ID)
            ?.observe(viewLifecycleOwner) {
                doctorId = it
            }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(Const.DOCTOR_ID)
            ?.observe(viewLifecycleOwner) {
                binding.selectDoctor.text = it
            }
        viewModel.createCallLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseState.Success -> {
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_createCallFragment_to_successfulCallFragment)
                    binding.loading.visibility = View.GONE
                }
                is ResponseState.Error -> {
                    showToast(state.message)
                    binding.loading.visibility = View.GONE
                }
                ResponseState.Loading -> binding.loading.visibility = View.VISIBLE
            }
        }
    }

    private fun noClicks() {
        binding.selectDoctor.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_createCallFragment_to_selectDoctorFragment)
        }
        binding.btnSendCall.setOnClickListener {
            validate()
        }

    }

    private fun validate() {
        val name = binding.editPatientName.text.toString()
        val age = binding.editAge.text.toString()
        val phone = binding.editPhone.text.toString()
        val description = binding.editDescription.text.toString()

        when {
            name == null -> showToast("Name")
            age == null -> showToast("Age")
            phone == null -> showToast("Phone")
            else -> viewModel.createCall(name, doctorId, age, phone, description)
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), "$message field is missed", Toast.LENGTH_SHORT).show()
    }

}