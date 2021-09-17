package com.example.hellokotlin.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionManagerImpl(context:Context) : SessionManager {
    private val shardPref:SharedPreferences
    private var session:Session ?= null

    companion object{
        val SHARED_PREFERENCE_NAME = "session"
        val KEY_SESSION = "key_session"
        val KEY_USERNAME = "key_username"
    }
    init {
        shardPref = context.applicationContext.getSharedPreferences(context.packageName+SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE)
    }
    override fun loadSession(): Session {
        session ?: run {
            val username = shardPref.getString(KEY_USERNAME,"") ?:  ""
            val sessionId = shardPref.getString(KEY_SESSION,"") ?:  ""
            session = Session(username,sessionId)
        }
        return session!!
    }

    override fun storeSession(s: Session) {
        shardPref.edit {
            putString(KEY_SESSION,s.sessionId)
            putString(KEY_USERNAME,s.username)
            apply().also {
                session = s
            }
        }
    }
}