package com.mobdeve.s15.gironasayasranario.cinereview.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s15.gironasayasranario.cinereview.R
import com.mobdeve.s15.gironasayasranario.cinereview.databinding.FragmentSchedulingBinding

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

    private val TAG = "Scheduling Fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            Log.d(TAG, "{param1: $param1, param2: $param2}")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSchedulingBinding.inflate(inflater)
        scheduleRV = binding.scheduleRv
        scheduleRV.adapter = ScheduleAdapter()
        Log.d(TAG, "OnCreateView()")
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Scheduling.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SchedulingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    class ScheduleAdapter: RecyclerView.Adapter<ScheduleViewHolder>() {
        private val TAG = "ScheduleAdapter"

        private val testData = arrayOf("9:00 PM", "10:00 PM", "11:00 PM")

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.showing_time_item, parent, false)

            return ScheduleViewHolder(view)
        }

        override fun getItemCount(): Int {
            return testData.size
        }

        override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
            Log.d(TAG, "IM BINDING")
            holder.bindData(testData[position])
        }
    }
    class ScheduleViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val timeTv: TextView = view.findViewById(R.id.sched_time_range_tv)
        private val statusIconIv: ImageView = view.findViewById(R.id.sched_status_iv)

        fun bindData(data: String) {
            timeTv.setText(data)
        }
    }
}