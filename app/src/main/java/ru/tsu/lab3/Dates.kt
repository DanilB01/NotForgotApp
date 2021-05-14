package ru.tsu.lab3

import ru.tsu.lab3.network.Category
import ru.tsu.lab3.network.Task

/*
data class Dates(
    val type: Int,
    val noteDB: NoteDB?,
    val categoryDB: CategoryDB?
)*/

data class Dates(
    val type: Int,
    val task: Task?,
    val category: Category?
)
