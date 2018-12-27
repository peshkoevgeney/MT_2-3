package com.example.myapplication123

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Example (

        @SerializedName("breeds")
        @Expose
        val breeds: List<Object>,
        @SerializedName("categories")
        @Expose
        val categories: List<Category>,
        @SerializedName("id")
        @Expose
        val id: String,
        @SerializedName("url")
        @Expose
        val url: String)