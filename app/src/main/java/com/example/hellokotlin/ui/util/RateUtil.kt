package com.example.hellokotlin.ui.util

import android.util.Log


/**
 *
 * Created by Davide Parise on 25/10/21.
 */
object RateUtil {
    private const val TAG = "RateUtil"

    /**
     * Convert rate from [0-5] to [0-10]
     * Used to convert the rate from WEB to APP
     * @param rate 0 not rated, 0.5 to 5
     * @return 0 not converted - number from 0.5 to 10 if is converted
     */
    fun convertRateForApp(rate: Float?): Float {
        Log.d(TAG, "convertRateForApp: $rate")
        if(rate == null || rate !in 0.5F..10F) return 0.0F
        return rate / 2
    }

    /**
     *
     * Convert rate from [0-10] to [0-5]
     * Used to convert the rate from APP to WEB
     * @param rate 0 not rated, form 0.5 to 10
     */
    fun convertRateForWeb(rate: Float?): Float {
        if(rate==null || rate !in 0.5..5.0) return 0F
        return rate * 2
    }

    fun toText(rate: Float?):String? {
        if (rate == null|| rate !in 0.5F..10F) return null
        return rate.toInt().toString()
    }
}