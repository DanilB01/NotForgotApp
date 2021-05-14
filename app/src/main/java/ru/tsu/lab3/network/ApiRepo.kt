package ru.tsu.lab3.network

class ApiRepo(private val api: Api) {

    suspend fun loginUser(user: UserLoginForm):Token = api.loginUser(user)

    suspend fun registerUser(user: UserRegistrationForm): User = api.registerUser(user)

    suspend fun getPriorities(token: String): Array<Priority> = api.getPriorities("Bearer $token")

    suspend fun getCategories(token: String): Array<Category> = api.getCategories("Bearer $token")

    suspend fun getTasks(token: String): Array<Task> = api.getTasks("Bearer $token")

    suspend fun addCategory(token: String, form: CategoryForm): Category = api.addCategory("Bearer $token", form)

    suspend fun addTask(token: String, form: TaskForm): Task = api.addTask("Bearer $token", form)

    suspend fun editTask(token: String, id: Int, form: TaskForm): Task = api.editTask("Bearer $token", id, form)

    suspend fun deleteTask(token: String, id: Int) = api.deleteTask("Bearer $token", id)

}