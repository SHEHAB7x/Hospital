package com.example.hospital.ui.hr

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.hospital.R
import com.example.hospital.databinding.FragmentHRHomeBinding
import com.example.hospital.utils.MySharedPreferences

class HRHomeFragment : Fragment() {
    private var _binding: FragmentHRHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHRHomeBinding.inflate(inflater, container, false)
        onClicks()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileName.text = MySharedPreferences.getUserName()
    }
    private fun onClicks() {
        binding.btnEmployee.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_HRHome_to_employeeList)
        }
        binding.profileTab.setOnClickListener{
            val id : Int =  MySharedPreferences.getUserId()
            val action = HRHomeFragmentDirections.actionHRHomeToProfile(id)
            Navigation.findNavController(it).navigate(action)
        }
    }
}