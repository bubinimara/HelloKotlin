package com.example.hellokotlin.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 *
 * Created by Davide Parise on 17/09/21.
 */
@Parcelize
data class Session(val username:String, val sessionId:String) : Parcelable{
    fun isValid():Boolean{
        return sessionId.isNotBlank() && username.isNotBlank()
    }
}
