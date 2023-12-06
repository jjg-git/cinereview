package com.mobdeve.s15.gironasayasranario.cinereview.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.mobdeve.s15.gironasayasranario.cinereview.R
import com.mobdeve.s15.gironasayasranario.cinereview.ReservationController
import com.mobdeve.s15.gironasayasranario.cinereview.databinding.FragmentSucessBinding

class SucessFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSucessBinding.inflate(layoutInflater)
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.mainFrag)
            ReservationController.resetTicket()
        }

        return binding.root
    }
}