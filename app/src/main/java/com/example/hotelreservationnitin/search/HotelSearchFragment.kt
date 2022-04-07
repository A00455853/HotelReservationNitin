package com.example.hotelreservationnitin.search

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.DatePicker
import com.example.hotelreservationnitin.hotels.HotelsFragment
import com.example.hotelreservationnitin.MainActivity
import com.example.hotelreservationnitin.R
import com.example.hotelreservationnitin.ReservationDate
import com.example.hotelreservationnitin.SharedPrefHelper

class HotelSearchFragment : Fragment() {

    lateinit var checkInDateTextView: TextView
    lateinit var checkOutDateTextView: TextView
    lateinit var numOfGuestsEditText: EditText
    lateinit var searchButton : Button
    private var isCheckInDate = false
    private var checkInDate : ReservationDate? = null
    private var checkOutDate : ReservationDate? = null
    private var numOfGuests = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hotel_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkInDateTextView = view.findViewById(R.id.checkInDateDisplayTextView)
        checkOutDateTextView = view.findViewById(R.id.checkOutDateDisplayTextView)
        numOfGuestsEditText = view.findViewById(R.id.numOfGuestsEditText)
        searchButton = view.findViewById(R.id.searchButton)

        checkInDateTextView.setOnClickListener {
            isCheckInDate = true
            com.example.hotelreservationnitin.DatePicker()
                .show(requireActivity().supportFragmentManager, "Check in Date")
        }

        checkOutDateTextView.setOnClickListener {
            com.example.hotelreservationnitin.DatePicker()
                .show(requireActivity().supportFragmentManager, "Checkout Date")
        }

        searchButton.setOnClickListener {
            if (validateData()) {
                    saveData()
                (activity as MainActivity).navigateToFragment(HotelsFragment.newInstance(1), true)
            }
        }
    }

    private fun saveData() {
        val sharedPreferences =
            activity?.getSharedPreferences(SharedPrefHelper.SHARED_PREF_FILE, MODE_PRIVATE)

        val editor = sharedPreferences?.edit()

        editor?.putInt(SharedPrefHelper.CHECK_IN_DATE_YEAR, checkInDate!!.year)
        editor?.putInt(SharedPrefHelper.CHECK_IN_DATE_MONTH, checkInDate!!.month)
        editor?.putInt(SharedPrefHelper.CHECK_IN_DATE_DAY, checkInDate!!.day)

        editor?.putInt(SharedPrefHelper.CHECK_OUT_DATE_YEAR, checkOutDate!!.year)
        editor?.putInt(SharedPrefHelper.CHECK_OUT_DATE_MONTH, checkOutDate!!.month)
        editor?.putInt(SharedPrefHelper.CHECK_OUT_DATE_DAY, checkOutDate!!.day)

        editor?.putInt(SharedPrefHelper.NUMBER_OF_GUESTS, numOfGuests)

        editor?.commit()

    }

    private fun validateData() : Boolean {
        if (checkInDate == null) {
            Toast.makeText(requireContext(), "Please provide Check in Date", Toast.LENGTH_LONG).show()
            return false
        }
        if (checkOutDate == null) {
            Toast.makeText(requireContext(), "Please provide Check out Date", Toast.LENGTH_LONG).show()
            return false
        }

        if (checkOutDate!! < checkInDate!!) {
            Toast.makeText(requireContext(), "Check In Date cannot be greater or same as Checkout date", Toast.LENGTH_LONG).show()
            return false
        }
        val numOfGuests = numOfGuestsEditText.text
        if (numOfGuests == null || numOfGuests.toString().isEmpty()) {
            Toast.makeText(requireContext(), "Please provide valid number of guests", Toast.LENGTH_LONG).show()
            return false
        }

        this.numOfGuests = Integer.parseInt(numOfGuests.toString())
        if (this.numOfGuests <= 0) {
            Toast.makeText(requireContext(), "Please provide valid number of guests", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HotelSearchFragment()
    }

    fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        if (isCheckInDate) {
            checkInDate = ReservationDate(year, month, dayOfMonth)
            checkInDateTextView.text = checkInDate!!.getDate()
        } else {
            checkOutDate = ReservationDate(year, month, dayOfMonth)
            checkOutDateTextView.text = checkOutDate!!.getDate()
        }
        isCheckInDate = false
    }
}