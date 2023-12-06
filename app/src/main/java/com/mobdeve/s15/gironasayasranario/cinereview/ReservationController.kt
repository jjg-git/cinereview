package com.mobdeve.s15.gironasayasranario.cinereview

import android.util.Log
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class ReservationController {
    companion object {
        var makingTicket: Ticket = Ticket()

        fun pickMovie(movie: String) {
            makingTicket.movieName = movie
            printDebugTicket()
        }

        fun pickCinema(cinema: Cinema) {
            makingTicket.cinema = cinema.name
            printDebugTicket()
        }

        fun pickSchedule(scheduleTime: ScheduleTime, status: String) {
            makingTicket.schedule = scheduleTime
            makingTicket.status = status
            printDebugTicket()
        }

        fun resetTicket() {
            makingTicket = Ticket()
            printDebugTicket()
        }

        fun printDebugTicket() {
            Log.d("Ticket", "{\n" +
                    "\tuserId: ${makingTicket.userId},\n" +
                    "\tschedule: {\n" +
                    "\t\tfromTime: ${makingTicket.schedule?.fromTime}\n" +
                    "\t\ttoTime: ${makingTicket.schedule?.toTime}\n" +
                    "\t},\n" +
                    "\tstatus: ${makingTicket.status},\n" +
                    "\tmovieName: ${makingTicket.movieName}," +
                    "\tcinema: ${makingTicket.cinema}" +
                    "\n}")
        }

        suspend fun reserveTicket(ticket: Ticket) {
            if (getCountTicket() < 1)
                return

            val newData = hashMapOf(
                "userId" to ticket.userId,
                "schedule" to ticket.schedule,
                "status" to ticket.status,
                "movieName" to ticket.movieName,
                "erased" to false
            )
            DBController.insertData("Tickets", newData)
        }

        suspend fun getTicket(movieName: String, scheduleTime: ScheduleTime): QuerySnapshot? {
            val scheduleMap = hashMapOf(
                "fromTime" to scheduleTime.fromTime.toString(),
                "toTime" to scheduleTime.toTime.toString()
            )
            val result = DBController.getDocument("Ticket")
                .whereEqualTo("movieName", movieName)
                .whereEqualTo("scheduleTime", scheduleMap)
                .get().await()

            return result
        }
        suspend fun getCountTicket():Int {
            val result = DBController.getData("Tickets").await()
            val countTicket = result.documents.size
            return countTicket
        }
    }
}