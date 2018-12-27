package com.example.myapplication123

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

        lateinit var category: List<Category>
        lateinit var catlist: List<Example>
        lateinit var context: Context
        lateinit var mydb: CatImageDatabase
        var counter = 0

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = applicationContext
        mydb = CatImageDatabase.getInstance(context)

        /*
        When you click on next or previous buttons, you store to
        Room the same catlist(s).
        When you click on room button, you can see data
        in db on Logs(log.i tag getAll).
        Rotate states are not supported.
        * */

        //Next Button
        button_next.setOnClickListener {
        counter++
        if (counter >= catlist.size - 1) {
        counter = 0
        }
                category = catlist.get(counter).categories ?: emptyList()
                Toast.makeText(applicationContext, "Next", Toast.LENGTH_LONG).show()
        Glide.with(context)
        .load(catlist.get(counter).url)
        .into(imageView_cat)
        Single.fromCallable({mydb.CatDao().insertCurrent(category)}).subscribeOn(Schedulers.io())
        .subscribe({
        Log.i("insertCurrent", "+++");
        }, {
        Log.i("Error ", "---");
        })
        }

        //Previous Button
        button_previous.setOnClickListener {
        Toast.makeText(applicationContext, "Previous", Toast.LENGTH_LONG).show()
        counter--
        if (counter <= 0) {
        counter = catlist.size - 1
        }
                category = catlist.get(counter).categories ?: emptyList()
                Glide.with(context)
        .load(catlist.get(counter).url)
        .into(imageView_cat)
        Single.fromCallable({mydb.CatDao().insertCurrent(category)}).subscribeOn(Schedulers.io())
        .subscribe({
        Log.i("insertCurrent ", "+++");
        }, {
        Log.i("Error ", "---");
        })
        }

        //Room Button
        button_room.setOnClickListener {
        Toast.makeText(applicationContext, "Room", Toast.LENGTH_LONG).show()

        mydb.CatDao().getAll()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
        Log.d("getAll", "category names" + it[0].name)
        },{
        Log.d("Error:getAll", "lols")
        })
        }

        //Check ImageView
        imageView_cat.setColorFilter(22)

        //Retrofit
        val retrofit = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/v1/images/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        val service = retrofit.create(CatApi::class.java)
        service.getExample()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
        catlist = it
        Glide.with(context)
        .load(catlist.get(2).url)
        .into(imageView_cat)
        Log.i("StringMessage", it.toString())
        }, {
        Log.i("Subscribe ", "Error")
        })
        }
        }
