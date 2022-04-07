package com.example.hotelreservationnitin.hotels

import com.example.hotelreservationnitin.datamodels.Hotel

interface OnHotelListItemClickListener {
    fun onItemClick(hotel: Hotel)
}