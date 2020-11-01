package id.ryanandri.dekontaminasi.utils

import java.text.DecimalFormat

class ValueFormater {
    fun decimalFormat(value : Int?) : String {
        val decimal  = DecimalFormat("#,###,###")
        val resDecimal : String = decimal.format(value)
        return resDecimal
    }
}