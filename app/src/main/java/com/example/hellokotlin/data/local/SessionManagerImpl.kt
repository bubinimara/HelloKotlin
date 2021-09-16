package com.example.hellokotlin.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionManagerImpl(context:Context) : SessionManager {
    private val shardPref:SharedPreferences
    private var sessionId:String = ""

    companion object{
        val SHARED_PREFERENCE_NAME = "session"
        val KEY_SESSION = "key_session"
    }
    init {
        shardPref = context.applicationContext.getSharedPreferences(context.packageName+SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE)
    }
    override fun loadSession(): String {
        sessionId.isEmpty().let {
            sessionId = shardPref.getString(KEY_SESSION,"") ?:  ""
        }
        return  sessionId
    }

    override fun storeSession(id: String) {
        shardPref.edit {
            putString(KEY_SESSION,id)
            commit().also {
                if(it) sessionId = id
            }

        }
    }
}