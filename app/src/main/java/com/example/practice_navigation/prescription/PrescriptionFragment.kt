package com.example.practice_navigation.prescription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.practice_navigation.databinding.FragmentPrescriptionBinding

class PrescriptionFragment : Fragment() {
    private lateinit var binding: FragmentPrescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            FragmentPrescriptionBinding.inflate(LayoutInflater.from(requireContext()), null, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}