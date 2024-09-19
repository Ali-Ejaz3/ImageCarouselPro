package com.example.unsplash

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var rvMain :RecyclerView
    lateinit var myAdapter: MyAdapter
    var BASE_URL="https://api.github.com"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain = findViewById(R.id.userRecyclerView)

        rvMain.layoutManager = LinearLayoutManager(this)

        getAllData()

    }

    private fun getAllData() {
        var retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)


        var retroData = retrofit.getData()


        retroData.enqueue(object : Callback<List<UserItem>>{
            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(
                call: Call<List<UserItem>>?,
                response: Response<List<UserItem>>?
            ) {

                var data = response?.body()!!
                myAdapter = MyAdapter(baseContext,data)
                rvMain.adapter = myAdapter

                Log.d("data",data.toString())

            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<List<UserItem>>?, t: Throwable?) {

            }

        })
    }
}