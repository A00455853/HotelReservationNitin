package com.example.hotelreservationnitin.datamodels

data class Hotel(val id: Int,
                 val hotelname: String,
                 val starrating: Float,
                 val city: String,
                 val country: String,
                 val address: String,
                 val rooms: List<Room>
)
