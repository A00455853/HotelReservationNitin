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
        clearData()
        hotelSearchFragment = HotelSearchFragment.newInstance()
        navigateToFragment(hotelSearchFragment, false)
    }
// medthod to perform the fragment txn
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


    private fun clearData() {
        val sharedPreferences =
            getSharedPreferences(SharedPrefHelper.SHARED_PREF_FILE, MODE_PRIVATE)

        val editor = sharedPreferences?.edit()

        editor?.putInt(SharedPrefHelper.CHECK_IN_DATE_YEAR, 0)
        editor?.putInt(SharedPrefHelper.CHECK_IN_DATE_MONTH, 0)
        editor?.putInt(SharedPrefHelper.CHECK_IN_DATE_DAY, 0)

        editor?.putInt(SharedPrefHelper.CHECK_OUT_DATE_YEAR, 0)
        editor?.putInt(SharedPrefHelper.CHECK_OUT_DATE_MONTH, 0)
        editor?.putInt(SharedPrefHelper.CHECK_OUT_DATE_DAY, 0)

        editor?.putInt(SharedPrefHelper.NUMBER_OF_GUESTS, 0)

        editor?.commit()

    }
}