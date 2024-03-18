package com.example.betterus_tutorial.pages

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.betterus_tutorial.R
import com.example.betterus_tutorial.databinding.FragmentSecondBinding
import com.example.betterus_tutorial.user.dataObjects.ActivityHolder
import com.example.betterus_tutorial.user.dataObjects.ActivityInfoLog
import com.example.betterus_tutorial.user.dataObjects.MealInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class SecondFragment : Fragment() {
    val fireDB = FirebaseDatabase.getInstance()
    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser = firebaseAuth.currentUser
    val userRef: DatabaseReference = fireDB.getReference("users").child(firebaseUser!!.uid)

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel

    val meditationActivities = mutableListOf<String>()
    val activityExercises = mutableListOf<String>()
    var allActivities2 = mutableListOf<String>()
    val activityExercisePairs = mutableListOf<Pair<String, Int>>()
    private var obtainedExerciseLog: ArrayList<ActivityInfoLog>? = null
    private var obtainedMeditationLog: ArrayList<ActivityInfoLog>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize Spinner
        val spinner = binding.activityInput
        spinner.prompt = "Select an option"

        // Load meditation exercises
        userRef.child("meditationInfo").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val meditationInfo: ActivityHolder? = dataSnapshot.getValue(ActivityHolder::class.java)

                meditationInfo?.let { info ->
                    for (i in 1..ActivityHolder.NUM_ACTIVITIES) {
                        info.getActivity(i)?.activityName?.let { meditationActivities.add(it) }
                    }
                }
                // Add "Select an option" at the beginning of the meditation list
                meditationActivities.add(0, "Select an option")
                populateSpinner()
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        // Load activity exercises
        userRef.child("exerciseInfo").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val activityInfo: ActivityHolder? = dataSnapshot.getValue(ActivityHolder::class.java)
                activityInfo?.let { info ->
                    for (i in 1..ActivityHolder.NUM_ACTIVITIES) {
                        val activity = info.getActivity(i)
                        activity?.let {
                            val activityName = it.activityName
                            activityExercises.add(activityName)
                            val calories = it.calPerHour
                            activityExercisePairs.add(Pair(activityName, calories)) // Add activity name and calories pair
                        }
                    }
                }
                // Populate spinner after fetching data
                populateSpinner()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that occur during the retrieval process
            }
        })








        userRef.child("userLog")
            .child("exerciseLog").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnap: DataSnapshot) {
                    val typeIndicator = object : GenericTypeIndicator<ArrayList<ActivityInfoLog>>() {}
                    obtainedExerciseLog = dataSnap.getValue(typeIndicator)
                }

                override fun onCancelled(dbError: DatabaseError) {
                    obtainedExerciseLog = null
                }
            })


        userRef.child("userLog")
            .child("meditationLog").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnap: DataSnapshot) {
                    val typeIndicator = object : GenericTypeIndicator<ArrayList<ActivityInfoLog>>() {}
                    obtainedMeditationLog = dataSnap.getValue(typeIndicator) ?: ArrayList()
                }

                override fun onCancelled(dbError: DatabaseError) {
                    obtainedMeditationLog = null
                }
            })








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

                if (position > 3) {
                    val selectedActivity = activityExercises[position - 4]
                    val calories = activityExercisePairs[position - 4].second
                    binding.caloriesText.text = "$calories"
                } else {
                    // Reset caloriesText for meditation activities
                    binding.caloriesText.text = ""
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

    private fun populateSpinner() {
        // Combine both lists
        val allActivities = mutableListOf<String>().apply {
            addAll(meditationActivities)
            addAll(activityExercises)
        }
        // Create ArrayAdapter for spinner
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            allActivities.toTypedArray()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.activityInput.adapter = adapter
        allActivities2 = allActivities
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigate to previous fragment
        binding.previousButton.setOnClickListener {
            val fragManager: FragmentManager = requireActivity().supportFragmentManager
            val firstFragmentInstance = FirstFragment() // Create an instance of FirstFragment
            fragManager.beginTransaction()
                .replace(R.id.fragmentSection, firstFragmentInstance)
                .addToBackStack(null)
                .commit()
        }


        binding.submitButton.setOnClickListener(View.OnClickListener { view -> //When submit button pressed

            //updates progress bar
            sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
            val selectedItemPosition = binding.activityInput.selectedItemPosition
            val selectedActivity = allActivities2[selectedItemPosition]


            if (selectedItemPosition != 0) {
                if (meditationActivities.contains(selectedActivity)) { //if Meditation activity



                    val selectedSpinnerItem = binding.activityInput.selectedItem.toString()
                    val chosenMeditationCopy = ActivityInfoLog(
                        selectedSpinnerItem,
                        0,
                        SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()),
                        0
                    )
                    obtainedMeditationLog?.add(chosenMeditationCopy)
                    userRef.child("userLog").child("meditationLog")
                        .setValue(obtainedMeditationLog)

                    sharedViewModel.updateMedDone(1)

                } else if (activityExercises.contains(selectedActivity)) {//if exercise activity
                    val caloriesBurned = if (selectedItemPosition > 3) {
                        activityExercisePairs[selectedItemPosition - 4].second // Get calories from the pair
                    } else {
                        0 // Default value for meditation activities
                    }

                    // Update the caloriesBurned variable in the shared view model
                    sharedViewModel.updateExDone(1)
                    sharedViewModel.updateCaloriesBurnt(caloriesBurned)


                    //TODO: Send back firebase data, these two include the name, and calories(if it's not meditation)
                    val selectedSpinnerItem = binding.activityInput.selectedItem.toString()
                    val caloriesText = binding.caloriesText.text.toString().toInt()

                    if (obtainedExerciseLog == null) obtainedExerciseLog = ArrayList()

                    val chosenExerciseCopy = ActivityInfoLog(
                        selectedSpinnerItem,
                        caloriesText,
                        SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()),
                        0
                    )
                    obtainedExerciseLog!!.add(chosenExerciseCopy)
                    userRef.child("userLog").child("exerciseLog")
                        .setValue(obtainedExerciseLog)
                }



                Toast.makeText(context, "Activity recorded!", Toast.LENGTH_SHORT).show()
            }
        })
    }}
