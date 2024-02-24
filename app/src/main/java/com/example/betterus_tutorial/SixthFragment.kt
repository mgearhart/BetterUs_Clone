package com.example.betterus_tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.betterus_tutorial.databinding.FragmentSixthBinding

class SixthFragment : Fragment() {

    private var _binding: FragmentSixthBinding? = null
    private val binding get() = _binding!!

    private var savedName: String? = null
    private var savedEmail: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSixthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_SixthFragment_to_FirstFragment)
        }

        if (savedInstanceState != null) {
            savedName = savedInstanceState.getString("name")
            savedEmail = savedInstanceState.getString("email")
        }

        // Set saved text to EditText fields if available
        savedName?.let { binding.editTextText2.setText(it) }
        savedEmail?.let { binding.editTextText.setText(it) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save text from EditText fields
        outState.putString("name", binding.editTextText2.text.toString())
        outState.putString("email", binding.editTextText.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}