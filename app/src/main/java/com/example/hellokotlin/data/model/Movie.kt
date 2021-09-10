package com.example.hellokotlin.data.model


/**
 *
 * Created by Davide Parise on 04/09/21.
 *
 * A movie item
 */
data class Movie(
    val id:Int,
    val title:String,
    var poster_path:String=""
)
