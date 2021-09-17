package com.example.hellokotlin.data.local


/**
 *
 * Created by Davide Parise on 16/09/21.
 */
interface SessionManager {

    fun loadSession():Session
    fun storeSession(sessionId:Session)
}