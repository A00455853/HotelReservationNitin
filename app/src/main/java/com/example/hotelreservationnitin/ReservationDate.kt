package com.example.hotelreservationnitin

class ReservationDate(val year: Int, val month: Int, val day: Int) {

    fun getDate () : String {
        return ""+ day + "/" + month + "/"+year
    }

    override fun toString () : String {
        return ""+ year + "-" + month + "-"+day
    }

    operator fun compareTo(date: ReservationDate): Int {
        return if (year > date.year
            || (year == date.year && month > date.month)
            || (year == date.year && month == date.month && day > date.day)
        )
            1
        else
            -1
    }
}
