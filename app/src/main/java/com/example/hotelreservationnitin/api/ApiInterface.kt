package com.example.hotelreservationnitin.api

import com.example.hotelreservationnitin.datamodels.BookRoom
import com.example.hotelreservationnitin.datamodels.BookRoomResult
import com.example.hotelreservationnitin.datamodels.Hotel
import com.example.hotelreservationnitin.datamodels.HotelList
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    // all hotels
    @GET("listHotel/{checkindate}/{checkoutdate}")
    suspend fun getHotelsList(@Path("checkindate") String checkindate,@Path("checkindate") String checkindate) : Response<List<Hotel>>

    // book a room
    @Headers("Content-Type: application/json")
    @POST("bookRoom")
    suspend fun bookRoom(@Body bookRoom: BookRoom) : Response<BookRoomResult>
}