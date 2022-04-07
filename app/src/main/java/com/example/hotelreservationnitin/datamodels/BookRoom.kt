package com.example.hotelreservationnitin.datamodels

import com.google.gson.annotations.SerializedName

data class BookRoom(
    @SerializedName("room_id") val room_id: Int,
    @SerializedName("userid") val userid: Int,
    @SerializedName("date_from") val date_from: String,
    @SerializedName("date_to") val date_to: String,
    @SerializedName("total_price") val total_price: Float,
    @SerializedName("guestList") val guestList: List<GuestDetail>,
)
