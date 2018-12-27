package com.example.myapplication123

import io.reactivex.Observable
import retrofit2.http.GET

    interface CatApi {
    @GET("search?size=full&mime_types=jpg,png,gif&format=json&order=RANDOM&page=0&limit=10&category_ids&breeds_ids")
    fun getExample(): Observable<List<Example>>
}