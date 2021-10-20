package com.example.hellokotlin.data.network.cache


/**
 *
 * Created by Davide Parise on 27/09/21.
 */
class InMemoryCache<T> {
    private var t:T ?= null

    fun get():T?{
        return t
    }
    fun set(t: T?){
        this.t = t
    }
}