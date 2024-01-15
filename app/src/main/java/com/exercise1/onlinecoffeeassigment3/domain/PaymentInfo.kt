package com.exercise1.onlinecoffeeassigment3.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentInfo(
    var paymentList:MutableList<String>

):Parcelable
