package com.example.hotelreservationnitin.congratulation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hotelreservationnitin.R

class CongratulationFragment : Fragment() {

    private var bookingRefNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            bookingRefNumber = it.getString(ARG_BOOKING_REF_NUMBER,"")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_congratulation, container, false)
        val bookingTextView = view.findViewById<TextView>(R.id.bookingTextView)
        bookingTextView.text = context?.getString(R.string.booking_ref_number, bookingRefNumber)
        return view
    }

    companion object {
        const val ARG_BOOKING_REF_NUMBER = "booking_ref_number"
        @JvmStatic
        fun newInstance(bookRefNum: String?) =
            CongratulationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_BOOKING_REF_NUMBER, bookRefNum)
                }
            }
    }
}