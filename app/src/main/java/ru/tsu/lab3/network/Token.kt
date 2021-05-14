package ru.tsu.lab3.network

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("api_token")
    var apiToken: String
)