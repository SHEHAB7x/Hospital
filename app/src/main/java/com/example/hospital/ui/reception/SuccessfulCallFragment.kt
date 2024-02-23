package com.example.hospital.ui.reception

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hospital.R
import com.example.hospital.databinding.FragmentCallsBinding
import com.example.hospital.databinding.FragmentSuccessfulCallBinding


class SuccessfulCallFragment : Fragment() {
    private var _binding : FragmentSuccessfulCallBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSuccessfulCallBinding.inflate(inflater,container,false)

        return binding.root
    }


}