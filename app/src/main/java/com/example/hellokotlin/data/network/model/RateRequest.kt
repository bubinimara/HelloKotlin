package com.example.hellokotlin.data.network.model


/**
 *
 * Created by Davide Parise on 05/10/21.
 */
class RateRequest (rate:Int) {
    companion object{
        const val MIN_VALUE = "0.5"
    }
    val value:String

    init {
        if (rate <= 0) value = "0.5"
        else if(rate >= 10 ) value = "10"
        else value = rate.toString()
    }
}