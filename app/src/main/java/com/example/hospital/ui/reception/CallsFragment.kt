package com.example.hospital.ui.reception

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.hospital.R
import com.example.hospital.adapters.AdapterCalls
import com.example.hospital.data.DataAllCalls
import com.example.hospital.databinding.FragmentCallsBinding
import com.example.hospital.network.ResponseState
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class CallsFragment : Fragment() {
    private var _binding: FragmentCallsBinding? = null
    private val binding get() = _binding!!
    private val callsViewModel: CallsViewModel by viewModels()
    private var adapterCalls: AdapterCalls? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callsViewModel.getCalls("")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCallsBinding.inflate(inflater, container, false)
        adapterCalls = AdapterCalls()
        onClicks()
        observers()
        updateRecyclerView()
        return binding.root
    }

    private fun observers() {
        callsViewModel.callsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Success -> {
                    binding.loading.visibility = View.GONE
                    adapterCalls?.list = it.data.data as ArrayList<DataAllCalls>
                    binding.recyclerCalls.adapter = adapterCalls
                    adapterCalls?.notifyDataSetChanged()
                }

                is ResponseState.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.loading.visibility = View.GONE
                }

                is ResponseState.Loading -> binding.loading.visibility = View.VISIBLE
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onClicks() {
        binding.btnDate.setOnClickListener {
            dataPicker()
        }
        binding.btnAddCall.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_callsFragment_to_createCallFragment)
        }
        binding.btnBack.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_callsFragment_to_receptionFragment)
        }
    }

    private fun updateRecyclerView() {
        binding.dateOfCall.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun afterTextChanged(s: Editable?) {
                s.toString().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                callsViewModel.getCalls(s.toString())
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dataPicker() {
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Select date")
        val materialDatePicker = builder.build()

        materialDatePicker.show(childFragmentManager, "DATE_PICKER")

        materialDatePicker.addOnPositiveButtonClickListener { selection ->
            val dateFormatter = DateTimeFormatter.ofPattern("dd . MM . yyyy")
            val selectedDate =
                Instant.ofEpochMilli(selection).atZone(ZoneId.systemDefault()).toLocalDate()
            binding.dateOfCall.text = selectedDate.format(dateFormatter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}