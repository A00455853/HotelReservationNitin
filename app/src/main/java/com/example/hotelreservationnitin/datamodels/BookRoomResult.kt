package com.example.hotelreservationnitin.datamodels

data class BookRoomResult(
    val id: Int,
    val user_id: Int,
    val room_id: Int,
    val date_from: String,
    val date_to: String,
    val total_price: Float,
    val book_ref_num: String,
    val guests: List<GuestDetail>,
)
