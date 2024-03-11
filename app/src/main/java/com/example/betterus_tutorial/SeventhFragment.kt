package com.example.betterus_tutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.betterus_tutorial.databinding.FragmentSeventhBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SeventhFragment : Fragment() {

    private var _binding: FragmentSeventhBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSeventhBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.continueButton.setOnClickListener {
            findNavController().navigate(R.id.action_SeventhFragment_to_FirstFragment)
        }
        binding.previousButton.setOnClickListener {
            findNavController().navigate(R.id.action_SeventhFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
