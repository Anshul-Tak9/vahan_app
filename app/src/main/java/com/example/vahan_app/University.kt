package com.example.vahan_app

data class University(
    val domains: List<String>,
    val name: String,
    val stateProvince: String?, // nullable
    val web_pages: List<String>,
    val country: String,
    val alphaTwoCode: String
)