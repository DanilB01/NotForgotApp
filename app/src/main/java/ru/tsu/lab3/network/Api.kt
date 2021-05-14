package ru.tsu.lab3.network

import retrofit2.http.*


interface Api {

    @POST("register")
    suspend fun registerUser(@Body registerForm: UserRegistrationForm): User

    @POST("login")
    suspend fun loginUser(@Body loginForm: UserLoginForm): Token

    @GET("priorities")
    suspend fun getPriorities(@Header("Authorization") token: String): Array<Priority>

    @GET("categories")
    suspend fun getCategories(@Header("Authorization") token: String): Array<Category>

    @GET("tasks")
    suspend fun getTasks(@Header("Authorization") token: String): Array<Task>

    @POST("categories")
    suspend fun addCategory(@Header("Authorization") token: String, @Body form: CategoryForm): Category

    @POST("tasks")
    suspend fun addTask(@Header("Authorization") token: String, @Body form: TaskForm): Task

    @PATCH("tasks/{id}")
    suspend fun editTask(@Header("Authorization") token: String, @Path("id") id: Int, @Body form: TaskForm): Task

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Header("Authorization") token: String, @Path("id") id: Int)
}