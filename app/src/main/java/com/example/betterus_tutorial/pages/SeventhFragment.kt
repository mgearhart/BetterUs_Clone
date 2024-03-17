package com.example.betterus_tutorial.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.betterus_tutorial.R
import com.example.betterus_tutorial.databinding.FragmentSeventhBinding
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

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

        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(requireContext()))
        }

        val python = Python.getInstance()
        val script = python.getModule("Recommender") // Assuming 'Recommender.py' is your Python module
        val jsonString = script.callAttr("recommender","arg1","arg2","arg3").toString()

        val jsonString2 = jsonString.substring(0, jsonString.length)



        //val jsonObject = org.json.JSONObject(jsonString2)


        //val value1 = jsonObject.getString("meal")
        //val value2 = jsonObject.getString("meditationActivity")
        //val value3 = jsonObject.getString("exerciseActivity")
        binding.recommendationText.text = jsonString2

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


