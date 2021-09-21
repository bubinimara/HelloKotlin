package com.example.hellokotlin.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
    @Entity(tableName = "account_state")
    data class AccountState constructor(
        @PrimaryKey val id: Int=-1,
        @ColumnInfo(name = "rate") val rate:Int=-1){ // add timestamp
    }
}
