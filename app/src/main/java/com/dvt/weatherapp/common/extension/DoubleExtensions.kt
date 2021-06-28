package com.dvt.weatherapp.common.extension

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.roundTo(numFractionDigits: Int): Double {
    return BigDecimal(this)
        .setScale(numFractionDigits, RoundingMode.HALF_EVEN)
        .toDouble()
}