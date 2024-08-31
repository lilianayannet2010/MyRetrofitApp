package com.example.miretrofitapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miretrofitapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// Define the API interface in a separate file if you need to reuse it elsewhere
interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @POST("users")
    fun createUser(@Body user: User): Call<User>

    @PUT("users/{id}")
    fun updateUser(@Path("id") id: Int, @Body user: User): Call<User>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: Int): Call<Void>
}

// Define the data class in a separate file if you need to reuse it elsewhere
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val website: String
)

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var apiService: ApiService
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        // Initialize RecyclerView
        adapter = UserAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Fetch users
        fetchUsers()

        // Example of creating a new user
        val newUser = User(50, "Liliana Bertullo", "liliana@example.com", "456-271-7890", "lilibe.com")
        createUser(newUser)

        // Example of updating a user
        val updatedUser = User(1, "Updated Name", "updatedemail@example.com", "098-765-4321", "updatedwebsite.com")
        updateUser(1, updatedUser)

        // Example of deleting a user
        deleteUser(1)
    }

    private fun fetchUsers() {
        apiService.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    adapter.updateData(response.body() ?: emptyList())
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // Handle error
            }
        })
    }

    private fun createUser(user: User) {
        apiService.createUser(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    // Handle success
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // Handle error
            }
        })
    }

    private fun updateUser(id: Int, user: User) {
        apiService.updateUser(id, user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    // Handle success
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // Handle error
            }
        })
    }

    private fun deleteUser(id: Int) {
        apiService.deleteUser(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Handle success
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle error
            }
        })
    }
}
// Mi primer App