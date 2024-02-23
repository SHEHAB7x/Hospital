package com.example.hospital.ui.reception

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.hospital.R
import com.example.hospital.databinding.FragmentReceptionBinding
import com.example.hospital.ui.hr.HRHomeFragmentDirections
import com.example.hospital.utils.MySharedPreferences


class ReceptionHomeFragment : Fragment() {
    private var _binding: FragmentReceptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceptionBinding.inflate(inflater, container, false)
        onClicks()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileName.text = MySharedPreferences.getUserName()
    }

    private fun onClicks() {
        binding.profileTab.setOnClickListener {
            val id: Int = MySharedPreferences.getUserId()
            val action = ReceptionHomeFragmentDirections.actionReceptionFragmentToProfile(id)
            Navigation.findNavController(it).navigate(action)
        }
        binding.btnCalls.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_receptionFragment_to_callsFragment)
        }
    }

}