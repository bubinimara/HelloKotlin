package com.example.hellokotlin.ui.adapter


/**
 *
 * Created by Davide Parise on 26/09/21.
 */
fun interface AdapterClickListener<in T> {
    fun onItemClicked(item:T)
}