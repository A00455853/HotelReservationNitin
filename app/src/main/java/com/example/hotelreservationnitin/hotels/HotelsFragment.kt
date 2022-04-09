package com.example.hotelreservationnitin.hotels

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.hotelreservationnitin.MainActivity
import com.example.hotelreservationnitin.R
import com.example.hotelreservationnitin.ReservationDate
import com.example.hotelreservationnitin.SharedPrefHelper
import com.example.hotelreservationnitin.api.ApiInterface
import com.example.hotelreservationnitin.api.RetrofitHelper
import com.example.hotelreservationnitin.datamodels.Hotel
import com.example.hotelreservationnitin.guestdetail.GuestDetailFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A fragment representing a list of Items.
 */
class HotelsFragment : Fragment(), OnHotelListItemClickListener {

    private var columnCount = 1
    private var selectedHotel : Hotel? = null
    private lateinit var list: RecyclerView
    private lateinit var subTitle: TextView
    private var progressBar : ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        getHotels()
    }

    private fun getHotels() {
        val hotelsApi = RetrofitHelper.getInstance().create(ApiInterface::class.java)
        GlobalScope.launch {
            val result = hotelsApi.getHotelsList()
            if (result?.body() != null) {
                withContext(Dispatchers.Main) {
                    progressBar?.visibility = GONE
                    list.adapter = HotelRecyclerViewAdapter(result.body()!!, this@HotelsFragment)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hotels_list, container, false)
        list = view.findViewById(R.id.list)
        subTitle = view.findViewById(R.id.subTitle)
        val nextButton = view.findViewById<Button>(R.id.nextButton)
        progressBar = view.findViewById(R.id.progress_circular)
        progressBar?.visibility = VISIBLE

        // Set the adapter
        if (list is RecyclerView) {
            with(list) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                //adapter = MyHotelRecyclerViewAdapter(hotels, this@HotelsFragment)
            }
        }

        setSubTitle()

        nextButton.setOnClickListener {
            if (validateData()) {
                saveData()
                (activity as MainActivity).navigateToFragment(GuestDetailFragment.newInstance(1), true)
            }
        }

        return view
    }
    private fun saveData() {
        val sharedPreferences =
            activity?.getSharedPreferences(SharedPrefHelper.SHARED_PREF_FILE, Context.MODE_PRIVATE)

        val editor = sharedPreferences?.edit()

        editor?.putFloat(SharedPrefHelper.HOTEL_PRICE, selectedHotel!!.rooms[0].price)


        editor?.commit()

    }

    private fun setSubTitle() {
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

            subTitle?.text = context?.getString(R.string.hotel_list_sub_title, count, checkInDate.getDate(), checkOutDate.getDate())
        }
    }

    private fun validateData(): Boolean {
         if (selectedHotel == null) {
             Toast.makeText(requireContext(), "Please select 1 hotel", Toast.LENGTH_LONG).show()
             return false
         }
        return true
    }

    override fun onItemClick(hotel: Hotel) {
        selectedHotel = hotel
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
        @JvmStatic
        fun newInstance(columnCount: Int) =
            HotelsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}