package com.example.practice_navigation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.practice_navigation.R
import com.example.practice_navigation.databinding.FragmentBlankBinding

class BlankFragment : Fragment() {
    private lateinit var binding: FragmentBlankBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentBlankBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_blankFragment_to_otherFragment)
        }
        binding.btnNext2.setOnClickListener {
            findNavController().navigate(R.id.action_blankFragment2_to_otherFragment2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}