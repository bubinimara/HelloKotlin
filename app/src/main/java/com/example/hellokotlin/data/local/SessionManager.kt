package com.example.hellokotlin.data.local


/**
 *
 * Created by Davide Parise on 16/09/21.
 */
interface SessionManager {

    fun loadSession():String
    fun storeSession(sessionId:String)
}