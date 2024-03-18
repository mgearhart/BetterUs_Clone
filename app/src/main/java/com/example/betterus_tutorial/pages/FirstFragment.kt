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
import com.example.betterus_tutorial.user.dataObjects.CurrentGoalStatus
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

    private lateinit var goalInfo: GoalInfo


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        userRef.child("currentGoalStatus").child("currentStatus").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentGoalStatus: GoalInfo? = dataSnapshot.getValue(GoalInfo::class.java)
                SharedViewModel.goalInfo2 = currentGoalStatus
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle onCancelled event if necessary
            }
        })

        // Fetch GoalInfo from Firebase
        userRef.child("goalInfo").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Deserialize GoalInfo from the dataSnapshot
                val goalInfoFromFirebase: GoalInfo? = dataSnapshot.getValue(GoalInfo::class.java)

                // Handle the retrieved GoalInfo
                goalInfoFromFirebase?.let {
                    goalInfo = it // Store GoalInfo here



                    // Set the initial progress
                    sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
                    val caloriesNeeded = goalInfo?.caloriesGained ?: 0
                    val numMed = goalInfo?.numMeditation ?: 0
                    val numEx = goalInfo?.numExercise ?: 0
                    val caloriesToBeBurned = goalInfo?.caloriesBurnt ?: 0

                    // Set the initial progress
                    binding.progressBar1.progress = calculateProgress(sharedViewModel.caloriesBurned, caloriesToBeBurned)
                    binding.progressBar4.progress = calculateProgress(sharedViewModel.caloriesEaten, caloriesNeeded)
                    binding.progressBar5.progress = calculateProgress(sharedViewModel.meditationDone, numMed)

                    updateProgressText(binding.progressBar1.progress, binding.textView1)
                    updateProgressText(binding.progressBar4.progress, binding.textView3)
                    updateProgressText(binding.progressBar5.progress, binding.textView4)


                    //This is the overall progress bar
                    val sum = binding.progressBar1.progress + binding.progressBar4.progress + binding.progressBar5.progress
                    binding.progressBar6.progress = if(sum == 0){
                        0
                    } else if (binding.progressBar1.progress >= 100 &&
                        binding.progressBar4.progress >= 100 &&
                        binding.progressBar5.progress >= 100
                    ) {
                        100
                    } else {
                        (sum / 3)
                    }
                    updateProgressText(binding.progressBar6.progress, binding.textView8)
                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
                val currentGoalStatus = CurrentGoalStatus()
                userRef.child("currentGoalStatus").child("currentStatus")
            }
        })
    }



    private fun calculateProgress(currentValue: Int, targetValue: Int): Int {
        return if (targetValue > 0) {
            ((currentValue.toFloat() / targetValue.toFloat()) * 100).coerceAtMost(100f).toInt()
        } else {
            0
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

    companion object {
        var goalInfo2: GoalInfo?= null
    }
    val fireDB = FirebaseDatabase.getInstance()
    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser = firebaseAuth.currentUser
    val userRef: DatabaseReference = fireDB.getReference("users").child(firebaseUser!!.uid)

    var caloriesEaten: Int = 0
    var caloriesBurned: Int = 0
    var exercisesDone: Int = 0
    var meditationDone: Int = 0
    //var goalInfo2: GoalInfo? = null
    //init {
    // Fetch currentGoalStatus from Firebase and update goalInfo2
    //    userRef.child("currentGoalStatus").child("currentStatus").addListenerForSingleValueEvent(object : ValueEventListener {
    //        override fun onDataChange(dataSnapshot: DataSnapshot) {
    //            val currentGoalStatus: GoalInfo? = dataSnapshot.getValue(GoalInfo::class.java)
    //            goalInfo2 = currentGoalStatus
    //        }

    //        override fun onCancelled(databaseError: DatabaseError) {
    // Handle onCancelled event if necessary
    //        }
    //    })
    //}


    fun updateCaloriesEaten(calories: Int) {
        caloriesEaten += calories
        saveUserGoal(caloriesBurned,caloriesEaten,exercisesDone,meditationDone)
    }
    fun updateCaloriesBurnt(calories: Int) {
        caloriesBurned += calories
        saveUserGoal(caloriesBurned,caloriesEaten,exercisesDone,meditationDone)
    }
    fun updateMedDone(num: Int) {
        meditationDone += num
        saveUserGoal(caloriesBurned,caloriesEaten,exercisesDone,meditationDone)

    }fun updateExDone(num: Int) {
        exercisesDone += num
        saveUserGoal(caloriesBurned,caloriesEaten,exercisesDone,meditationDone)
    }


    fun saveUserGoal(caloriesBurnt: Int, caloriesGained: Int,numExercise: Int, numMeditation: Int) {
        val updatedGoalInfo = GoalInfo(caloriesBurnt,caloriesGained,numExercise,numMeditation)

        userRef.child("currentGoalStatus")
            .child("currentStatus").setValue(updatedGoalInfo)
    }

    //companion object {
    //    var goalInfo2: GoalInfo?= null
    //}
}