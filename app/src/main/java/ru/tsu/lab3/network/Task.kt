package ru.tsu.lab3.network

data class Task(
    val id: Int,
    val title: String,
    val description: String?,
    val done: Int,
    val deadline: Int,
    val category: Category,
    val priority: Priority,
    val created: Int
)