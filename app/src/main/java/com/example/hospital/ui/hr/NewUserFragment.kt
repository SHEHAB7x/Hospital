package com.example.hospital.ui.hr

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.hospital.R
import com.example.hospital.data.Const
import com.example.hospital.data.ModelUser
import com.example.hospital.databinding.FragmentNewUserBinding
import com.example.hospital.network.ResponseState
import com.example.hospital.utils.MySharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class NewUserFragment : Fragment() {
    private var _binding: FragmentNewUserBinding? = null
    private val binding get() = _binding!!
    private val hrViewModel: HRViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewUserBinding.inflate(inflater, container, false)
        onClicks()
        setupDropdowns()
        setupTextWatchers()
        observeLoginResult()
        return binding.root
    }

    private fun observeLoginResult() {
        hrViewModel.userRegistrationResult.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is ResponseState.Success -> navigateToHome(state.data)
                is ResponseState.Error -> showError(state.message)
                ResponseState.Loading -> showLoadingIndicator()
            }
        })
    }

    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                with(binding) {
                    editEmail.setBackgroundResource(
                        if (editEmail.text.toString()
                                .isEmpty()
                        ) R.drawable.container_gray else R.drawable.container_green
                    )
                    editPassword.setBackgroundResource(
                        if (editPassword.text.toString()
                                .isEmpty()
                        ) R.drawable.container_gray else R.drawable.container_green
                    )
                    editFirstName.setBackgroundResource(
                        if (editFirstName.text.toString()
                                .isEmpty()
                        ) R.drawable.container_gray else R.drawable.container_green
                    )
                    editLastName.setBackgroundResource(
                        if (editLastName.text.toString()
                                .isEmpty()
                        ) R.drawable.container_gray else R.drawable.container_green
                    )
                    editPhone.setBackgroundResource(
                        if (editPhone.text.toString()
                                .isEmpty()
                        ) R.drawable.container_gray else R.drawable.container_green
                    )
                    editAddress.setBackgroundResource(
                        if (editAddress.text.toString()
                                .isEmpty()
                        ) R.drawable.container_gray else R.drawable.container_green
                    )
                }
            }
        }
        binding.editEmail.addTextChangedListener(textWatcher)
        binding.editPassword.addTextChangedListener(textWatcher)
        binding.editAddress.addTextChangedListener(textWatcher)
        binding.editLastName.addTextChangedListener(textWatcher)
        binding.editPhone.addTextChangedListener(textWatcher)
        binding.editFirstName.addTextChangedListener(textWatcher)
    }

    private fun setupAdapter(autoCompleteTextView: AutoCompleteTextView, items: List<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_list_item, items)
        autoCompleteTextView.setAdapter(adapter)

    }

    private fun onClicks() {
        binding.btnBack.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_newUser_to_employeeList)
        }

        binding.btnCreateNewUser.setOnClickListener {
            validate()
        }
        binding.dob.setOnClickListener {
            dataPicker()
        }
    }

    private fun dataPicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "${selectedYear}-${selectedMonth + 1}-${selectedDay}"
            binding.dob.text = selectedDate
        }, year, month, day)
        datePickerDialog.show()
    }

    private fun validate() {
        val firstName = binding.editFirstName.text.toString()
        val lastName = binding.editLastName.text.toString()
        val gender = binding.genderAutoCompleteTextView.text.toString()
        val specialist = binding.specialistAutoCompleteTextView.text.toString()
        val birthday = binding.dob.text.toString()
        val status = binding.statusAutoCompleteTextView.text.toString()
        val phone = binding.editPhone.text.toString()
        val email = binding.editEmail.text.toString()
        val address = binding.editAddress.text.toString()
        val password = binding.editPassword.text.toString()
        val type = MySharedPreferences.getUserType()

        if (firstName.isEmpty()) binding.editFirstName.setBackgroundResource(R.drawable.container_error)
        else if (lastName.isEmpty()) binding.editLastName.setBackgroundResource(R.drawable.container_error)
        else if (gender.isEmpty()) binding.gender.setBackgroundResource(R.drawable.container_error)
        else if (specialist.isEmpty()) binding.specialist.setBackgroundResource(R.drawable.container_error)
        else if (birthday.isEmpty()) binding.dob.setBackgroundResource(R.drawable.container_error)
        else if (status.isEmpty()) binding.status.setBackgroundResource(R.drawable.container_error)
        else if (email.isEmpty()) binding.editEmail.setBackgroundResource(R.drawable.container_error)
        else if (address.isEmpty()) binding.editAddress.setBackgroundResource(R.drawable.container_error)
        else if (password.isEmpty()) binding.editPassword.setBackgroundResource(R.drawable.container_error)
        else {
            hrViewModel.registerUser(
                email,
                password,
                firstName,
                lastName,
                gender,
                specialist,
                birthday,
                status,
                address,
                phone,
                type
            )
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoadingIndicator() {

    }

    private fun navigateToHome(user: ModelUser) {
        Toast.makeText(requireContext(),"New user added successfully!",Toast.LENGTH_SHORT).show()
        val action = R.id.action_newUser_to_HRHome
        action.let {
            findNavController().navigate(it)
        }

    }

    private fun setupDropdowns() {
        val genderItems = listOf(Const.MALE, Const.FEMALE)
        val statusItems = listOf(Const.SINGLE, Const.MARRIED, Const.ENGAGED)
        val specialistItems = listOf(Const.DOCTOR, Const.HR, Const.NURSE)

        setupAdapter(binding.genderAutoCompleteTextView, genderItems)
        setupAdapter(binding.statusAutoCompleteTextView, statusItems)
        setupAdapter(binding.specialistAutoCompleteTextView, specialistItems)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}