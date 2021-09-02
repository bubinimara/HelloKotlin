package com.example.hellokotlin.data.model

/**
 * @author Davide Parise
 *
 */
data class User(var name:String){
    var isRoot: Boolean = name.equals("root")
}
