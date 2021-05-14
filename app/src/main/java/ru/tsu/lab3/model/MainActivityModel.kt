package ru.tsu.lab3.model

import android.content.Context
import ru.tsu.lab3.SharedPreference
import ru.tsu.lab3.network.Network

class MainActivityModel(context: Context) {
    private val sharedPreference= SharedPreference(context)

    fun getCurrentToken() {
        Network.token = sharedPreference.getValueString("token")!!
    }

}