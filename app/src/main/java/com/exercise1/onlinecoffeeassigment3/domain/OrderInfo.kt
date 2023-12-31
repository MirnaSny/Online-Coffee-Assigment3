package com.exercise1.onlinecoffeeassigment3.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderInfo(
    var orderList:MutableList<String>
):Parcelable

// when i want to send my data from fragment to fragment i have to make a object -> dataType = " Parcelable"
// so i have to use one of them = "bundle or safeArgs " to make easy for pass data between different parts of a program
