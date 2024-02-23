package com.example.hospital.ui.reception

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.hospital.R
import com.example.hospital.databinding.FragmentCreateCallBinding


class CreateCallFragment : Fragment() {
    private var _binding: FragmentCreateCallBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateCallBinding.inflate(inflater,container,false)
        noClicks()
        return binding.root
    }

    private fun noClicks() {
        binding.selectDoctor.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_createCallFragment_to_selectDoctorFragment)
        }

    }

}