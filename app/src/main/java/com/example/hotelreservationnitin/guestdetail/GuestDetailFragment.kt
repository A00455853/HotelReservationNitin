package com.example.hotelreservationnitin.guestdetail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Toast.LENGTH_LONG
import com.example.hotelreservationnitin.MainActivity
import com.example.hotelreservationnitin.R
import com.example.hotelreservationnitin.ReservationDate
import com.example.hotelreservationnitin.SharedPrefHelper
import com.example.hotelreservationnitin.api.ApiInterface
import com.example.hotelreservationnitin.api.RetrofitHelper
import com.example.hotelreservationnitin.congratulation.CongratulationFragment
import com.example.hotelreservationnitin.datamodels.BookRoom
import com.example.hotelreservationnitin.datamodels.GuestDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A fragment representing a list of Items.
 */
class GuestDetailFragment : Fragment() {

    private var columnCount = 1
    private var guestDetailList : MutableList<GuestDetail> = mutableListOf()
    private lateinit var userNameEditText : EditText
    private var progressBar : ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guest_detail, container, false)
        val list = view.findViewById<RecyclerView>(R.id.list)
        val nextButton = view.findViewById<Button>(R.id.nextButton)
        userNameEditText = view.findViewById(R.id.userNameEditText)
        progressBar = view.findViewById(R.id.progress_circular)
        showSavedData(view)

        // Set the adapter
        if (list is RecyclerView) {
            with(list) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                val sharedPreferences =
                    activity?.getSharedPreferences(SharedPrefHelper.SHARED_PREF_FILE, Context.MODE_PRIVATE)
               sharedPreferences?.let {
                   val count = sharedPreferences?.getInt(SharedPrefHelper.NUMBER_OF_GUESTS, 0)

                   for(i in 1..count) {
                       guestDetailList.add(GuestDetail("","", 0))
                   }
                   adapter = GuestDetailRecyclerViewAdapter(guestDetailList)
               }
            }
        }

        nextButton.setOnClickListener {
            if (validateData()) {
                saveData()
            }
        }
        return view
    }

    private fun showSavedData(view: View?) {
        view?.let {
            val sharedPreferences =
                activity?.getSharedPreferences(SharedPrefHelper.SHARED_PREF_FILE, Context.MODE_PRIVATE)

            sharedPreferences?.let {
                val count = it.getInt(SharedPrefHelper.NUMBER_OF_GUESTS, 0)

                val checkInYear = it.getInt(SharedPrefHelper.CHECK_IN_DATE_YEAR, 0)
                val checkInMonth = it.getInt(SharedPrefHelper.CHECK_IN_DATE_MONTH, 0)
                val checkInDay = it.getInt(SharedPrefHelper.CHECK_IN_DATE_DAY, 0)

                val checkOutYear = it.getInt(SharedPrefHelper.CHECK_OUT_DATE_YEAR, 0)
                val checkOutMonth = it.getInt(SharedPrefHelper.CHECK_OUT_DATE_MONTH, 0)
                val checkOutDay = it.getInt(SharedPrefHelper.CHECK_OUT_DATE_DAY, 0)

                val checkInDate = ReservationDate(checkInYear, checkInMonth, checkInDay)
                val checkOutDate = ReservationDate(checkOutYear, checkOutMonth, checkOutDay)

                val dateFrom = checkInDate.getDate()
                val dateTo = checkOutDate.getDate()

                view.findViewById<TextView>(R.id.checkInDateDisplayTextView).text = dateFrom
                view.findViewById<TextView>(R.id.checkOutDateDisplayTextView).text = dateTo
                view.findViewById<TextView>(R.id.numberOfGuestsDisplayTextView).text = count.toString()

            }
        }
    }

    private fun saveData() {

        val sharedPreferences =
            activity?.getSharedPreferences(SharedPrefHelper.SHARED_PREF_FILE, Context.MODE_PRIVATE)

        var dateFrom: String = ""
        var dateTo: String = ""

        sharedPreferences?.let {

            val checkInYear = it.getInt(SharedPrefHelper.CHECK_IN_DATE_YEAR, 0)
            val checkInMonth = it.getInt(SharedPrefHelper.CHECK_IN_DATE_MONTH, 0)
            val checkInDay = it.getInt(SharedPrefHelper.CHECK_IN_DATE_DAY, 0)

            val checkOutYear = it.getInt(SharedPrefHelper.CHECK_OUT_DATE_YEAR, 0)
            val checkOutMonth = it.getInt(SharedPrefHelper.CHECK_OUT_DATE_MONTH, 0)
            val checkOutDay = it.getInt(SharedPrefHelper.CHECK_OUT_DATE_DAY, 0)

            val checkInDate = ReservationDate(checkInYear, checkInMonth, checkInDay)
            val checkOutDate = ReservationDate(checkOutYear, checkOutMonth, checkOutDay)

            dateFrom = checkInDate.toString()
            dateTo = checkOutDate.toString()

        }

        val bookRoom = BookRoom(
            userid = Integer.parseInt(userNameEditText.text.toString()),
            room_id = 0,
            date_from = "2022-03-15",//dateFrom,
            date_to = "2022-03-20",//dateTo,
            total_price = 0f,
            guestList = guestDetailList
        )
        progressBar?.visibility = View.VISIBLE
        val bookRoomApi = RetrofitHelper.getInstance().create(ApiInterface::class.java)
        GlobalScope.launch {
            val result = bookRoomApi.bookRoom(bookRoom)
            if (result?.body() != null) {
                withContext(Dispatchers.Main) {
                    progressBar?.visibility = View.GONE
                    (activity as MainActivity).navigateToFragment(CongratulationFragment.newInstance(result?.body()?.book_ref_num), true)
                }
            }
        }
    }

    private fun validateData(): Boolean {
        if (userNameEditText.text.isNullOrEmpty()) {
            Toast.makeText(context, "Please provide Username", LENGTH_LONG).show()
            return false
        }
        if (guestDetailList.isEmpty()) {
            Toast.makeText(context, "Please provide Guest Detail", LENGTH_LONG).show()
            return false
        } else {
            for (item in guestDetailList) {
                if(item.isGuestDetailMissing()) {
                    Toast.makeText(context, "Please provide Guest Detail", LENGTH_LONG).show()
                    return false
                }
            }
        }
        return true
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            GuestDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}