package com.mudasir.foodtrackingapp.utils

import java.util.Locale

fun Double.formatPrice(): String {
    return "${Constants.CURRENCY_SYMBOL}${String.format(Locale.US, "%.0f", this)}"
}
