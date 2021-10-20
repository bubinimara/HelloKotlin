package com.example.hellokotlin.data.network.model

import com.google.gson.annotations.SerializedName

data class SessionResponse(@SerializedName("session_id") val sessionId:String?, val success:Boolean=false)
