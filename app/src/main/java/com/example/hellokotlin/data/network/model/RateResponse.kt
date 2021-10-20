package com.example.hellokotlin.data.network.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass


/**
 *
 * Created by Davide Parise on 05/10/21.
 */
@JsonClass(generateAdapter = false)
data class RateResponse(
    @SerializedName("status_code")val status_code:Int,
    @SerializedName("status_message")val status_message:String)
