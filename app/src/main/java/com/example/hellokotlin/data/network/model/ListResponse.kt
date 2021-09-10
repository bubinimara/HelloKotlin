package com.example.hellokotlin.data.network.model

import com.example.hellokotlin.data.model.User
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 *
 * Created by Davide Parise on 09/09/21.
 */
@JsonClass(generateAdapter = false)
data class ListResponse<T>(
    @SerializedName("results")
    var results:List<T>,
    @SerializedName("page")
    var page:Int,
    @SerializedName("total_pages")
    var totalPages:String

)
