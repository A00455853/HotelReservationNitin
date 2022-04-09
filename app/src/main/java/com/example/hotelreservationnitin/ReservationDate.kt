package com.example.hotelreservationnitin

class ReservationDate(val year: Int, val month: Int, val day: Int) {

    fun getDate () : String {
        var dayString = ""+day
        var monthString =""+month
        if(day<10) {
             dayString = "0" + day
        }
        if(month<10){
             monthString ="0"+month
        }
        return ""+ dayString + "/" + monthString + "/"+year
    }

    override fun toString () : String {
        var dayString = ""+day
        var monthString =""+month
        if(day<10) {
            dayString = "0" + day
        }
        if(month<10){
            monthString ="0"+month
        }
        return ""+ year + "-" + monthString + "-"+dayString
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
