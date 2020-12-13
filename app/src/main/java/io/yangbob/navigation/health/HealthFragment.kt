package io.yangbob.navigation.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.yangbob.navigation.databinding.FragmentHealthBinding

class HealthFragment : Fragment() {
    private lateinit var binding: FragmentHealthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHealthBinding.inflate(LayoutInflater.from(requireContext()), null, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}