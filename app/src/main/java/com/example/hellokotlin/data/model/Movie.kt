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
    @ColumnInfo(name = "title") var title:String,
    @ColumnInfo(name = "poster_path") val poster_path:String="",
    @ColumnInfo(name = "overview") val overview:String="",
    @Ignore var accountState:AccountState ?= null
){
    constructor(id:Int, title:String,poster_path:String="",overview:String="")
            : this(id,title,poster_path,overview,null)


    @JsonAdapter(AccountStateDeserializer::class)
    @Entity(tableName = "account_state")
    data class AccountState constructor(
        @PrimaryKey val id: Int=-1,
        @ColumnInfo(name = "rate") val rate:Int=-1){ // add timestamp
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as AccountState

            if (id != other.id) return false
            if (rate != other.rate) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + rate
            return result
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false
        if (title != other.title) return false
        if (poster_path != other.poster_path) return false
        if (overview != other.overview) return false
        if (accountState != other.accountState) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + poster_path.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + (accountState?.hashCode() ?: 0)
        return result
    }
}
