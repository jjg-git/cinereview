package com.mobdeve.s15.gironasayasranario.cinereview

data class ScheduleTime(
    val fromTime: Time,
    val toTime: Time,
) {
    override fun toString(): String {
        return "$fromTime - ${toTime.toString().padStart(2, '0')}"
    }

    fun toHashMap():HashMap<String, String> {
        return hashMapOf(
            "fromTime" to fromTime.toString(),
            "toTime" to toTime.toString()
        )
    }
}

data class Time (
    var hour: Int,
    var minute: Int,
    var amPM: AMPM
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
    constructor(seconds: Int): this(0, 0, AMPM.AM) {
        this.hour = seconds / 60
        this.minute = seconds / 60 / 60

        if (hour > 12) {
            hour -= 12
            amPM = AMPM.PM
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

    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "hour" to hour,
            "minute" to minute,
            "amPM" to when (amPM) {
                AMPM.AM -> "AM"
                AMPM.PM -> "PM"
            }
        )
    }
}

enum class AMPM {
    AM,
    PM
}