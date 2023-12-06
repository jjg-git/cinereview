package com.mobdeve.s15.gironasayasranario.cinereview

class Ticket(
    var userId: String = "",
    var schedule: ScheduleTime? = null,
    var status: String = "",
    var movieName: String = "",
    var cinema: String = "") {
    companion object {
        val AVAILABLE = "AVAILABLE"
        val RESERVED = "RESERVED"
        val DELETED = "DELETED"
    }
}