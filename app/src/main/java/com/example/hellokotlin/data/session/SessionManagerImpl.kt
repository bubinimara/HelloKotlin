package com.example.hellokotlin.data.session

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

    override fun storeSession(session: Session) {
        shardPref.edit {
            putString(KEY_SESSION,session.sessionId)
            putString(KEY_USERNAME,session.username)
            apply().also {
                this@SessionManagerImpl.session = session
            }
        }
    }

    override fun clear(): Boolean {
        session = null
        shardPref.edit().clear().apply()
        return true
    }
}