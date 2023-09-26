package com.example.vahan_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.universityListView)

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://universities.hipolabs.com/") // API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val universityService = retrofit.create(UniversityService::class.java)

        // Fetch university data
        universityService.getUniversities().enqueue(object : Callback<List<University>> {
            override fun onResponse(
                call: Call<List<University>>,
                response: Response<List<University>>
            ) {
                if (response.isSuccessful) {
                    val universities = response.body()
                    if (universities != null) {


                        val adapter = ArrayAdapter(
                            this@MainActivity,
                            android.R.layout.simple_list_item_1,
                            universities.map { "${it.name}, ${it.country}, ${it.web_pages.get(0)}" }
                        )
                        listView.adapter = adapter

                        listView.onItemClickListener =
                            AdapterView.OnItemClickListener { _, _, position, _ ->
                                val selectedUniversity = universities[position]
                                openWebView(selectedUniversity.web_pages[0])
                            }
                    }
                }
            }

            override fun onFailure(call: Call<List<University>>, t: Throwable) {
                // Handle API request failure
                println(t)
            }
        })

        // Start the UniversityRefreshService
        val serviceIntent = Intent(this, UniversityRefreshService::class.java)
        startService(serviceIntent)
    }

    private fun openWebView(url: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }
}
