package com.example.boni_football_score_viewer.utils.extensions

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


fun String.DateTransformator() : String{
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val currentDate = sdf.parse(this)

    val dateFormat = SimpleDateFormat("EE, dd MMM yyyy", Locale("id","ID"))
    val dates = dateFormat.format(currentDate)
    Timber.e(dates.toString())

    return dates.toString()
}