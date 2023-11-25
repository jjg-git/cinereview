package com.mobdeve.s15.gironasayasranario.cinereview

data class ScheduleTime(
    val fromTime: Time,
    val toTime: Time,
) {
    override fun toString(): String {
        return "$fromTime - ${toTime.toString().padStart(2, '0')}"
    }


}

data class Time (
    val hour: Int,
    val minute: Int,
    val amPM: AMPM
) {
    companion object {
        fun convertToTime(timeStr: String): Time {
            var hourInt: Int
            var minuteInt: Int
            var newAMPM: AMPM

            // TODO: Substring from 2 characters from start
            hourInt = timeStr.substring()

            return Time(hourInt, minuteInt, newAMPM)
        }
    }
    fun compareTo(time: Time):Int {
        if (this.hour == time.hour && this.minute == time.minute) {
            return 0
        }
        else {
            return this.toMinutes()- time.toMinutes()
        }
    }

    fun toMinutes(): Int {
        var minutes: Int = this.minute + this.hour * 60

        if (this.amPM == AMPM.PM && this.hour != 12) {
            minutes += 12 * 60
        }
        return minutes
    }

    override fun toString(): String {
        var amPMStr: String
        when (this.amPM) {
            AMPM.AM -> amPMStr = "AM"
            AMPM.PM -> amPMStr = "PM"
        }
        return "$hour:$minute ${amPMStr}"
    }
}

enum class AMPM {
    AM,
    PM
}