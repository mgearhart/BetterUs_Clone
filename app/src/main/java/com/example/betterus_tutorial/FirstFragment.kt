package com.example.betterus_tutorial
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import android.widget.TextView
import com.example.betterus_tutorial.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the initial progress
        binding.progressBar1.progress = 75
        binding.progressBar4.progress = 50
        binding.progressBar5.progress = 25
        updateProgressText(binding.progressBar1.progress, binding.textView1)
        updateProgressText(binding.progressBar4.progress, binding.textView3)
        updateProgressText(binding.progressBar5.progress, binding.textView4)


        binding.imageView4.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SixthFragment)
        }

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment)
        }
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_FourthFragment)
        }
        binding.buttonThird.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_FifthFragment)
        }
        binding.buttonFourth.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SeventhFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Update the TextView with the percentage of progress
    private fun updateProgressText(progress: Int, textView: TextView) {
        val progressText = "$progress%"
        textView.text = progressText
    }
}