package ru.tsu.lab3.network

import com.google.gson.annotations.SerializedName

data class TaskForm(
    val title: String?,
    val description: String?,
    var done: Int?,
    val deadline: Int?,
    @SerializedName("category_id")
    val categoryID: Int?,
    @SerializedName("priority_id")
    val priorityID: Int?
)