package com.example.hellokotlin.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageUrl(
    val base_url:String,
    val poster_sizes:List<String>,
    val profile_sizes:List<String>
) : Parcelable