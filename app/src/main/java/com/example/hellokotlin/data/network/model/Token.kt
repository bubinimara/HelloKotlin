package com.example.hellokotlin.data.network.model

import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


/**
 *
 * Created by Davide Parise on 09/09/21.
 */
@JsonClass(generateAdapter = false)
@Parcelize
data class Token(
    @SerializedName("expires_at")
    val expires_at:String="",
    @SerializedName("request_token")
    val request_token:String=""
    ):Parcelable
