package com.example.hellokotlin.data.session


/**
 *
 * Created by Davide Parise on 16/09/21.
 */
interface SessionManager {

    fun loadSession():Session
    fun storeSession(session:Session)
    fun clear():Boolean
}