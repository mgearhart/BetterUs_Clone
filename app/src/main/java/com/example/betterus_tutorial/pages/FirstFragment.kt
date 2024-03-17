package com.example.betterus_tutorial.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.betterus_tutorial.databinding.FragmentFirstBinding
import com.example.betterus_tutorial.user.dataObjects.GoalInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel

    val fireDB = FirebaseDatabase.getInstance()
    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser = firebaseAuth.currentUser
    val userRef: DatabaseReference = fireDB.getReference("users").child(firebaseUser!!.uid)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        userRef.child("goalInfo").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Deserialize GoalInfo from the dataSnapshot
                val goalInfo: GoalInfo? = dataSnapshot.getValue(GoalInfo::class.java)

                // Handle the retrieved GoalInfo
                goalInfo?.let {

                    // Set the goal information in the ViewModel
                    sharedViewModel.setGoalInfo(it)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle cancellation, if needed
            }
        })












        // Set the initial progress
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        binding.progressBar1.progress = sharedViewModel.progress1.value ?: 0
        binding.progressBar4.progress = sharedViewModel.progress2.value ?: 0
        binding.progressBar5.progress = sharedViewModel.progress3.value ?: 0

        // Set the initial progress
        sharedViewModel.progress1.observe(viewLifecycleOwner) { progress ->
            val caloriesBurnt = sharedViewModel.caloriesBurned
            val caloriesToBeBurned = sharedViewModel.caloriesToBeBurned

            // Calculate progress as a float to preserve decimal values
            val calculatedProgress = if (caloriesToBeBurned != null && caloriesToBeBurned > 0) {
                ((caloriesBurnt.toFloat() / caloriesToBeBurned.toFloat()) * 100).coerceAtMost(100f)
            } else {
                0f // Default to 0 if caloriesToBeBurned is null or 0
            }.toInt() // Convert the calculated progress to an integer

            binding.progressBar1.progress = calculatedProgress
            updateProgressText(calculatedProgress, binding.textView1)
        }

        sharedViewModel.progress2.observe(viewLifecycleOwner) { progress ->
            val caloriesEaten = sharedViewModel.caloriesEaten
            val caloriesNeeded = sharedViewModel.caloriesNeeded

            // Calculate progress as a float to preserve decimal values
            val calculatedProgress = if (caloriesNeeded > 0) {
                ((caloriesEaten.toFloat() / caloriesNeeded.toFloat()) * 100).coerceAtMost(100f)
            } else {
                0f // Default to 0 if caloriesNeeded is 0
            }.toInt() // Convert the calculated progress to an integer

            binding.progressBar4.progress = calculatedProgress
            updateProgressText(calculatedProgress, binding.textView3)
        }

        sharedViewModel.progress3.observe(viewLifecycleOwner) { progress ->
            val numMeditation = sharedViewModel.numMed ?: 0 // Get the value of numEx from SharedViewModel, default to 0 if null

            // Calculate progress as a float to preserve decimal values
            val calculatedProgress = if (numMeditation > 0) {
                ((progress.toFloat() / numMeditation.toFloat()) * 100).coerceAtMost(100f)
            } else {
                0f // Default to 0 if numMeditation is 0
            }.toInt() // Convert the calculated progress to an integer

            binding.progressBar5.progress = calculatedProgress
            updateProgressText(calculatedProgress, binding.textView4)
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
                binding.progressBar6.progress = (sum/3)
                updateProgressText((sum/3), binding.textView8)
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
    private var goalInfo: GoalInfo? = null
    val progress1 = MutableLiveData<Int>().apply { value = 0 }
    val progress2 = MutableLiveData<Int>().apply { value = 0 }
    val progress3 = MutableLiveData<Int>().apply { value = 0 }

    val fireDB = FirebaseDatabase.getInstance()
    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser = firebaseAuth.currentUser
    val userRef: DatabaseReference = fireDB.getReference("users").child(firebaseUser!!.uid)


    val caloriesNeeded: Int
        get() = goalInfo?.caloriesGained ?: 0

    val numMed: Int?
        get() = goalInfo?.numMeditation

    val numEx: Int?
        get() = goalInfo?.numExercise

    val caloriesToBeBurned: Int?
        get() = goalInfo?.caloriesBurnt

    // Locally modified variables
    var caloriesEaten: Int = 0
    var caloriesBurned: Int = 0


    // Function to increment progress counters
    fun incrementProgress(progress: MutableLiveData<Int>) {
        progress.value = (progress.value ?: 0) + 1
    }

    // Function to set GoalInfo
    fun setGoalInfo(info: GoalInfo) {
        goalInfo = info
    }

    // Function to get GoalInfo
    fun getGoalInfo(): GoalInfo? {
        return goalInfo
    }

    fun updateCaloriesEaten(calories: Int) {
        caloriesEaten += calories
        saveUserGoal()
    }

    fun updateCaloriesBurnt(calories: Int) {
        caloriesBurned += calories
        saveUserGoal()
    }

    fun saveUserGoal(){
        // Override user's old current goal status upon exiting app
        userRef.child("currentGoalStatus")
            .child("currentStatus").setValue(goalInfo);
    }
}
