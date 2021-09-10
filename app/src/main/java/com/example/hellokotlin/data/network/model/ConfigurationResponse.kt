package com.example.hellokotlin.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 *
 * Created by Davide Parise on 10/09/21.
 */
@Parcelize
data class ConfigurationResponse(
    val images: Image
) : Parcelable {
    @Parcelize
    data class Image(
        val base_url:String,
        val poster_sizes:List<String>,
        val profile_sizes:List<String>
    ) : Parcelable
}
