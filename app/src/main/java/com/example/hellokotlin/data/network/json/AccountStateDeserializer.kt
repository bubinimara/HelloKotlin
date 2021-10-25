package com.example.hellokotlin.data.network.json

import android.util.Log
import com.example.hellokotlin.data.model.Movie
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type


/**
 *
 * Created by Davide Parise on 20/09/21.
 */
class AccountStateDeserializer(): JsonDeserializer<Movie.AccountState> {



    private companion object{
        val TAG: String = "AccountStateDeser"
        val KEY_ID = "id"
        val KEY_RATED = "rated"
        val KEY_VALUE = "value"
    }
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Movie.AccountState {
        if(json == null) return Movie.AccountState()
        var rated=0F
        var id = -1

        try {
            id = json.asJsonObject.get(KEY_ID)!!.asInt
            rated = json.asJsonObject.get(KEY_RATED).asJsonObject.get(KEY_VALUE).asFloat
        } catch (e: Exception) {
            Log.e(TAG, "deserialize: ",e )
        }
        return Movie.AccountState(id,rated)
    }
}