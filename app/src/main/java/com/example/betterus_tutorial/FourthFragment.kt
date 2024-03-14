package com.example.betterus_tutorial

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.betterus_tutorial.databinding.FragmentFourthBinding
import com.example.betterus_tutorial.user.dataObjects.ActivityHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener








class FourthFragment : Fragment() {

    private var _binding: FragmentFourthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFourthBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize Spinner
        val spinner = binding.activityInput
        spinner.prompt = "Select an option"

        //set up Firebase stuff
        val fireDB = FirebaseDatabase.getInstance()
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        val userRef: DatabaseReference = fireDB.getReference("users").child(firebaseUser!!.uid)


        userRef.child("meditationInfo").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // dataSnapshot contains the data retrieved from Firebase
                val meditationInfo: ActivityHolder? = dataSnapshot.getValue(ActivityHolder::class.java)

                // Now we can use the retrieved meditationInfo object as needed
                meditationInfo?.let { info ->
                    val meditationNames = mutableListOf<String>()
                    meditationNames.add("Select an option")
                    for (i in 1..ActivityHolder.NUM_ACTIVITIES) {//this grabs the meditation activities from tutorial on firebase
                        info.getActivity(i)?.activityName?.let { meditationNames.add(it) }
                    }

                    val meditationNamesArray = meditationNames.toTypedArray()//stored meditation activities here

                    val adapter = ArrayAdapter(//populates the spinner
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        meditationNamesArray
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter



                    // Set listener on spinner to enable/disable submit button based on selection
                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            // Enable submit button if an option other is selected
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
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that occur during the retrieval process
            }
        })






        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val popupFragment: HeartrateFragment = HeartrateFragment()
        popupFragment.show(childFragmentManager, "fragment_heartrate")

        // Navigate to previous fragment
        binding.previousButton.setOnClickListener {
            findNavController().navigate(R.id.action_FourthFragment_to_FirstFragment)
        }

        // Navigate to next fragment
        binding.submitButton.setOnClickListener {

            //These 2 lines below grab the selected item from the spinner, convert to string
            val selectedItemPosition = binding.activityInput.selectedItemPosition
            val selectedActivityName = binding.activityInput.selectedItem.toString()

            val firebaseAuth = FirebaseAuth.getInstance()
            val firebaseUser = firebaseAuth.currentUser
            val fireDB = FirebaseDatabase.getInstance()

            //TODO: Insert code here that sends selected activity to Firebase

            findNavController().navigate(R.id.action_FourthFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
