package com.example.hospital.ui.hr

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.hospital.R
import com.example.hospital.adapters.AdapterEmployeeList
import com.example.hospital.data.Const
import com.example.hospital.data.DataAllUsers
import com.example.hospital.data.ModelUser
import com.example.hospital.databinding.FragmentEmployeeListBinding
import com.example.hospital.network.ResponseState
import com.example.hospital.utils.MySharedPreferences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeListFragment
    : Fragment() {

    private var _binding: FragmentEmployeeListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterEmployeeList: AdapterEmployeeList
    private val profileViewModel: ProfileViewModel by viewModels()
    private val viewModel: EmployeeListViewModel by viewModels()
    private var type = "All"
    private lateinit var listUsers: ArrayList<DataAllUsers>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAllUsers()
        adapterEmployeeList = AdapterEmployeeList(object : AdapterEmployeeList.OnItemClickListener {
            @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
            override fun onItemClick(user: DataAllUsers) {
                profileViewModel.getUserProfile(user.id)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmployeeListBinding.inflate(inflater, container, false)
        onClicks()
        setupSearchEditText()
        return binding.root
    }

    private fun setupSearchEditText() {
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.filterUsers(s.toString())
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
    }

    private fun getAllUsers() {
        viewModel.getUsers(type)
    }

    private fun observers() {
        viewModel.employeeListLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseState.Success -> {
                    binding.loading.visibility = View.GONE
                    listUsers = state.data.data as ArrayList<DataAllUsers>
                    adapterEmployeeList.list = listUsers
                    binding.employeeRecycler.adapter = adapterEmployeeList
                }
                is ResponseState.Error -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
                ResponseState.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
            }
        }

        profileViewModel.profileLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Success -> {
                    binding.loading.visibility = View.GONE
                    navigateToProfile(it.data)
                }
                is ResponseState.Error -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else -> binding.loading.visibility = View.VISIBLE
            }
        }

        viewModel.filteredUsers.observe(viewLifecycleOwner) { filteredUsers ->
            adapterEmployeeList.list = filteredUsers
            adapterEmployeeList.notifyDataSetChanged()
        }
    }


    private fun navigateToProfile(user: ModelUser) {
        val id: Int = user.data.id
        val action = EmployeeListFragmentDirections.actionEmployeeListToProfile(id,
            getString(R.string.fromemp))
        Navigation.findNavController(binding.root).navigate(action)
    }

    private fun onClicks() {
        binding.btnBack.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_employeeList_to_HRHome)
        }
        binding.btnAddNewUser.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_employeeList_to_newUser)
        }


        val buttons = listOf(
            binding.btnAll,
            binding.btnDoctor,
            binding.btnNurse,
            binding.btnHR,
            binding.btnAnalysis
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                buttons.forEach {
                    it.updateStyle(it == button, requireContext())
                }

                when (it) {
                    binding.btnHR -> viewModel.getUsers(Const.HR)
                    binding.btnNurse -> viewModel.getUsers(Const.NURSE)
                    binding.btnDoctor -> viewModel.getUsers(Const.DOCTOR)
                    binding.btnAnalysis -> viewModel.getUsers(Const.ANALYSIS)
                    binding.btnAll -> viewModel.getUsers(type)
                }
            }
        }

    }

    private fun TextView.updateStyle(isActive: Boolean, context: Context) {
        val backgroundRes = if (isActive) R.drawable.edit_item_green else R.drawable.edit_item
        val textColorRes = if (isActive) R.color.white else R.color.black
        this.background = ContextCompat.getDrawable(context, backgroundRes)
        this.setTextColor(ContextCompat.getColor(context, textColorRes))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}