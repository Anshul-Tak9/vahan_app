package com.example.vahan_app

import retrofit2.Call
import retrofit2.http.GET

interface UniversityService {
    @GET("search")
    fun getUniversities(): Call<List<University>>
}
