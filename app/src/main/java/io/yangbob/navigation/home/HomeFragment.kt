package io.yangbob.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.yangbob.navigation.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        binding.btnGoBlank.setOnClickListener {
            // safe args - 종속성 추가 후, rebuild해야 사용 가능
            val action =
                HomeFragmentDirections.actionHomeFragmentToBlankFragment("홈에서 출발했어요")
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}