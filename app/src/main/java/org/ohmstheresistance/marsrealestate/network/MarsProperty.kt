package org.ohmstheresistance.marsrealestate.network

import com.squareup.moshi.Json

data class MarsProperty(
    val id: String,
    @Json(name = "img_src") val img_srcUrl: String,
    val type: String,
    val price: Double)