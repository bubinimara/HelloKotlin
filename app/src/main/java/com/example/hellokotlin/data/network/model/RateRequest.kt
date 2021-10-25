package com.example.hellokotlin.data.network.model


/**
 *
 * Created by Davide Parise on 05/10/21.
 */
class RateRequest (rate:Float) {
    val value:String

    init {
        if (rate < 0) value = "0"
        else if(rate >= 10 ) value = "10"
        else value = rate.toString()
    }
}