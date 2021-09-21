package com.example.hellokotlin.data.model

import com.example.hellokotlin.data.network.json.AccountStateDeserializer
import com.google.gson.annotations.JsonAdapter


/**
 *
 * Created by Davide Parise on 04/09/21.
 *
 * A movie item
 */
data class Movie(
    val id:Int,
    val title:String,
    val poster_path:String="",
    var accountState:AccountState ?= null
){
    @JsonAdapter(AccountStateDeserializer::class)
    data class AccountState constructor(val id: Int=-1,val rate:Int=-1){
    }
}
