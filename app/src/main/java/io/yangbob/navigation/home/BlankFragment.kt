package io.yangbob.navigation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.yangbob.navigation.R
import io.yangbob.navigation.databinding.FragmentBlankBinding

class BlankFragment : Fragment() {
    private lateinit var binding: FragmentBlankBinding

    val args: BlankFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentBlankBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        val msg = args.msg
        binding.msg = msg

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_blankFragment_to_otherFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}