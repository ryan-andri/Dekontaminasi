package id.ryanandri.dekontaminasi.utils

import android.text.format.DateFormat
import java.util.*

class TimeStamp2Date() {
    fun convert(ts : Long?) : String {
        val cal : Calendar = Calendar.getInstance()
        if (ts != null) {
            cal.timeInMillis = ts
            return DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString()
        }
        return ""
    }
}