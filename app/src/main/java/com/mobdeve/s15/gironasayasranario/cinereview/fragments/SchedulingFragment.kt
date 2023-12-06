package com.mobdeve.s15.gironasayasranario.cinereview.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.BlendMode
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.common.base.Predicates.instanceOf
import com.mobdeve.s15.gironasayasranario.cinereview.DBController
import com.mobdeve.s15.gironasayasranario.cinereview.R
import com.mobdeve.s15.gironasayasranario.cinereview.ReservationController
import com.mobdeve.s15.gironasayasranario.cinereview.ScheduleTime
import com.mobdeve.s15.gironasayasranario.cinereview.Ticket
import com.mobdeve.s15.gironasayasranario.cinereview.Time
import com.mobdeve.s15.gironasayasranario.cinereview.databinding.FragmentSchedulingBinding
import com.mobdeve.s15.gironasayasranario.cinereview.databinding.ShowingTimeItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.Async.Schedule
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.reflect.typeOf

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SchedulingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SchedulingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var scheduleRV: RecyclerView
    private lateinit var binding: FragmentSchedulingBinding
    private lateinit var data: ArrayList<HashMap<String, Any>>

    private val TAG = "Scheduling Fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            Log.d(TAG, "{param1: $param1, param2: $param2}")
        }

        data = ArrayList()
        Log.d(TAG, "onCreate()")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSchedulingBinding.inflate(inflater)
        scheduleRV = binding.scheduleRv

        initializeShowingTime()
        Log.d(TAG, "OnCreateView()")
        return binding.root
    }

    fun initializeShowingTime() {
        val getTicket = DBController.getData("Tickets")

        Log.d(TAG, "inside initializeShowingTime()")
        GlobalScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Loading...")

            for (item in getTicket.await().documents) {
                val fromTimeStr
                    = (item["schedule"] as Map<String, Map<String, String>>)["fromTime"] as String
                val toTimeStr
                    = (item["schedule"] as Map<String, Map<String, String>>)["toTime"] as String
                val fromTime = Time.convertToTime(fromTimeStr)
                val toTime = Time.convertToTime(toTimeStr)

                val scheduleTime = ScheduleTime(fromTime, toTime)

                data.add(hashMapOf(
                    "scheduleTime" to scheduleTime,
                    "status" to item["status"] as String,
                ))
            }

            withContext(Dispatchers.Main) {
                val scheduleAdapter = ScheduleAdapter(data, this@SchedulingFragment)
                scheduleRV.adapter = scheduleAdapter
            }
        }
    }

    class ScheduleAdapter(
        val data : ArrayList<HashMap<String, Any>>,
        val fragment: SchedulingFragment)
        : RecyclerView.Adapter<ScheduleViewHolder>() {
        private val TAG = "ScheduleAdapter"

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ShowingTimeItemBinding.inflate(inflater, parent, false)

            return ScheduleViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
            Log.d(TAG, "IM BINDING")
            val schedule = data[position]["scheduleTime"] as ScheduleTime
            val status = data[position]["status"] as String

            holder.binding.root.setOnClickListener {
                if (status == Ticket.AVAILABLE) {
                    ReservationController.pickSchedule(schedule, status)
                    makeToast("Reservation made!!",
                        holder.binding.root.context)
                    fragment.findNavController().navigate(R.id.reservationPending)
                }
                else {
                    makeToast("It's reserved!!",
                        holder.binding.root.context)
                }
            }

            if (data[position]["status"] != Ticket.DELETED)
                holder.bindData(data[position])
        }
    }
    class ScheduleViewHolder(val binding: ShowingTimeItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val timeTv: TextView = binding.schedTimeRangeTv
        private val statusIconIv: ImageView = binding.schedStatusIv

        fun bindData(data: HashMap<String, Any>) {
            val schedule = data["scheduleTime"] as ScheduleTime
            val status = data["status"] as String
            when (status) {
                Ticket.AVAILABLE -> {
                    statusIconIv.setImageResource(R.drawable.available_circle)
                }
                Ticket.RESERVED -> {
                    statusIconIv.setImageResource(R.drawable.reserved_circle)
                }
            }



            timeTv.setText(schedule.toString())
        }
    }
    companion object {
        fun makeToast(message: String, context: Context) {
            val toast = Toast(context)
            toast.setText(message)
            toast.show()
        }
    }
}