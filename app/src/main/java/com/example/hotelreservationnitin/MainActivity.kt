package com.example.hotelreservationnitin

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hotelreservationnitin.search.HotelSearchFragment

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var hotelSearchFragment: HotelSearchFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hotelSearchFragment = HotelSearchFragment.newInstance()
        navigateToFragment(hotelSearchFragment, false)
    }

    fun navigateToFragment(fragment: Fragment, addToBackStack: Boolean) {
        // Begin the transaction
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // Replace the contents of the container with the new fragment
        fragmentTransaction.replace(R.id.frame_layout, fragment)

        if (addToBackStack) {
            // Replace the contents of the container with the new fragment
            fragmentTransaction.addToBackStack(fragment.tag)
        }

        // Complete the changes added above
        fragmentTransaction.commit()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        hotelSearchFragment.onDateSet(
            view = view,
            year = year,
            month = month,
            dayOfMonth = dayOfMonth
        )
    }
}