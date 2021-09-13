package com.example.hellokotlin.data.network.model

import android.os.Parcelable
import com.example.hellokotlin.data.model.ImageUrl
import kotlinx.parcelize.Parcelize


/**
 *
 * Created by Davide Parise on 10/09/21.
 */
@Parcelize
data class ConfigurationResponse(
    val images: ImageUrl
) : Parcelable {
}
