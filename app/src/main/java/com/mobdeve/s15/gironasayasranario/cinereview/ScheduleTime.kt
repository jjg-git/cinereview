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
        fun convertToTime(timeRawStr: String): Time {
            val timeFormatRegex: Regex =
                Regex("((1[0-2]|0?[1-9]):([0-5][0-9]) ?([AaPp][Mm]))")
            val amPMRegex: Regex =
                Regex("(([AaPp][Mm]))")
            val hhMMRegex: Regex =
                Regex("((1[0-2]|0?[1-9]):([0-5][0-9]))")

            var hourInt: Int = 0
            var minuteInt: Int = 0
            var newAMPM: AMPM = AMPM.AM


            // Show error if the input does not follow HH:MM PM format
            assert(timeFormatRegex.matches(timeRawStr) == true)

            val hhMMStr = timeRawStr.substring(hhMMRegex.find(timeRawStr)!!.range) // Outputs HH:MM
            val amPMStr = timeRawStr.substring(amPMRegex.find(timeRawStr)!!.range) // Outputs either AM or PM

            val hhMMSplit = hhMMStr.split(":") // Outputs [HH, MM]

            hourInt = hhMMSplit[0].toInt()
            minuteInt = hhMMSplit[1].toInt()

            when (amPMStr) {
                "AM" -> newAMPM = AMPM.AM
                "PM" -> newAMPM = AMPM.PM
            }

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