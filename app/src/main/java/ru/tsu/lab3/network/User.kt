package ru.tsu.lab3.network

import com.google.gson.annotations.SerializedName

data class User(
    var email: String,
    var name: String,
    var id:	Int,
    @SerializedName("api_token")
    var apiToken: String
)