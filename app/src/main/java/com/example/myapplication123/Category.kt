package com.example.myapplication123

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Category (

        @PrimaryKey
        var imageurlid: Int,

        @SerializedName("id")
        @Expose
        val id: Int,
        @SerializedName("name")
        @Expose
        val name: String)
