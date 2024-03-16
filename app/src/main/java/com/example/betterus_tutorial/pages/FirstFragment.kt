package com.example.betterus_tutorial.pages
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.betterus_tutorial.R
import com.example.betterus_tutorial.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel

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
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        binding.progressBar1.progress = sharedViewModel.progress1.value ?: 0
        binding.progressBar4.progress = sharedViewModel.progress2.value ?: 0
        binding.progressBar5.progress = sharedViewModel.progress3.value ?: 0

        // Set the initial progress
        sharedViewModel.progress1.observe(viewLifecycleOwner) { progress ->
            if(progress == 1) {
                binding.progressBar1.progress = 33
                updateProgressText(33, binding.textView1)
            }else if(progress == 2){
                binding.progressBar1.progress = 67
                updateProgressText(67, binding.textView1)
            }else if(progress >= 3){
                binding.progressBar1.progress = 100
                updateProgressText(100, binding.textView1)
            }
        }

        sharedViewModel.progress2.observe(viewLifecycleOwner) { progress ->
            if(progress == 1) {
                binding.progressBar4.progress = 33
                updateProgressText(33, binding.textView3)
            }else if(progress == 2){
                binding.progressBar4.progress = 67
                updateProgressText(67, binding.textView3)
            }else if(progress >= 3){
                binding.progressBar4.progress = 100
                updateProgressText(100, binding.textView3)
            }
        }

        sharedViewModel.progress3.observe(viewLifecycleOwner) { progress ->
            if (progress == 1) {
                binding.progressBar5.progress = 33
                updateProgressText(33, binding.textView4)
            } else if (progress == 2) {
                binding.progressBar5.progress = 67
                updateProgressText(67, binding.textView4)
            } else if (progress >= 3) {
                binding.progressBar5.progress = 100
                updateProgressText(100, binding.textView4)
            }
        }

        //This is the overall progress bar
        sharedViewModel.progress3.observe(viewLifecycleOwner) { progress ->
            val sum = binding.progressBar1.progress + binding.progressBar4.progress + binding.progressBar5.progress

            if(sum == 0){
                binding.progressBar6.progress = 0
            } else if (binding.progressBar1.progress >= 100 &&
                binding.progressBar4.progress >= 100 &&
                binding.progressBar5.progress >= 100
            ) {

                binding.progressBar6.progress = 100
                updateProgressText(100, binding.textView8)
            } else {
                binding.progressBar6.progress = (sum/9)*3
                updateProgressText((sum/9)*3, binding.textView8)
            }

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

class SharedViewModel : ViewModel() {
    // Progress counter variables
    val progress1 = MutableLiveData<Int>().apply { value = 0 }
    val progress2 = MutableLiveData<Int>().apply { value = 0 }
    val progress3 = MutableLiveData<Int>().apply { value = 0 }

    // Function to increment progress counters
    fun incrementProgress(progress: MutableLiveData<Int>) {
        progress.value = (progress.value ?: 0) + 1
    }
}
