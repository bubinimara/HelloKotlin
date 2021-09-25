package com.example.hellokotlin.data.model

import androidx.room.*
import com.example.hellokotlin.data.network.json.AccountStateDeserializer
import com.google.gson.annotations.JsonAdapter


/**
 *
 * Created by Davide Parise on 04/09/21.
 *
 * A movie item
 */
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey val id:Int,
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "poster_path") val poster_path:String="",
    @Ignore var accountState:AccountState ?= null
){
    constructor(id:Int, title:String,poster_path:String="")
            : this(id,title,poster_path,null){}



    @JsonAdapter(AccountStateDeserializer::class)
    @Entity(tableName = "account_state")
    data class AccountState constructor(
        @PrimaryKey val id: Int=-1,
        @ColumnInfo(name = "rate") val rate:Int=-1){ // add timestamp
    }
}
