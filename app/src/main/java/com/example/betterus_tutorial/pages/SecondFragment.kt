package com.example.betterus_tutorial.pages

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.betterus_tutorial.R
import com.example.betterus_tutorial.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize Spinner
        val spinner = binding.activityInput
        spinner.prompt = "Select an option"

        // Create ArrayAdapter for spinner
        val stringArray = arrayOf("None", "Exercise1", "Exercise2", "Exercise3")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            stringArray
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Set listener on spinner to enable/disable submit button based on selection
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Enable submit button if an option other than "None" is selected
                binding.submitButton.isEnabled = position != 0
                // Change submit button color based on enabled/disabled state
                if (position == 0) {
                    binding.submitButton.setBackgroundColor(Color.GRAY)
                } else {
                    val customBackground = requireContext().resources.getDrawable(R.drawable.button_soft_enabled_2, null)
                    binding.submitButton.background = customBackground // Change to desired color
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Disable submit button if nothing is selected
                binding.submitButton.isEnabled = false
                // Change submit button color to gray when disabled
                binding.submitButton.setBackgroundColor(Color.GRAY)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigate to previous fragment
        binding.previousButton.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        // Navigate to next fragment
        binding.submitButton.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
