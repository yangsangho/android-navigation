package io.yangbob.navigation.dose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.yangbob.navigation.R
import io.yangbob.navigation.databinding.FragmentDoseBinding

class DoseFragment: Fragment() {
    private lateinit var binding: FragmentDoseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDoseBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        binding.btnGoBlank.setOnClickListener {
            findNavController().navigate(R.id.action_doseFragment_to_blankFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}