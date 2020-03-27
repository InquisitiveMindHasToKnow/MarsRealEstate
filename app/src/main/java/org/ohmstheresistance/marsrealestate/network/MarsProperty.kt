package org.ohmstheresistance.marsrealestate.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarsProperty(
    val id: String,
    @Json(name = "img_src") val img_srcUrl: String,
    val type: String,
    val price: Double): Parcelable