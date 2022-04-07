package com.example.hotelreservationnitin.datamodels

import com.google.gson.annotations.SerializedName

data class GuestDetail(
    @SerializedName("name") var name: String,
    @SerializedName("gender") var gender: String,
    @SerializedName("age") var age: Int
) {
    fun isGuestDetailMissing(): Boolean {
        if (name.isNullOrEmpty() || age.equals(0) || gender.isNullOrEmpty())
            return true
        return false
    }
}
