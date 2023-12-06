package com.mobdeve.s15.gironasayasranario.cinereview.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mobdeve.s15.gironasayasranario.cinereview.R
import com.mobdeve.s15.gironasayasranario.cinereview.ReservationController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ReservationPendingFragment : Fragment(R.layout.fragment_reservation_pending) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch(Dispatchers.IO) {
            val reserve = async {
                ReservationController.reserveTicket(ReservationController.makingTicket)
            }
            val result = reserve.await()
            // if (result == FAIL}
            withContext(Dispatchers.Main) {
                findNavController().navigate(R.id.sucessFragment)
            }
        }
    }
}