package com.example.user.healthhelper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_recomendations.*

class recomendation (title : String, content : String) {
    private val Header = title
    private val Content = content
    private var IsChecked = false


    fun getTitle() : String {
        return Header
    }

    fun getContent() : String {
        return Content
    }

    fun checked() {
        IsChecked = true
    }
}

class RecomendationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendations)

        var isPersonalDataFull : Boolean = false

        var recomendations = Array<recomendation> (0, {i -> recomendation("", "")})
        val data = mutableListOf<String>()

        var eatenproducts_list = TotalDatabaseHandler(this)
        var products_list = ProductDatabaseHandler(this)
        val params_list = PersonDatabaseHandler(this)

        val arr = params_list.FetchProducts("bread", true)
        for (pers in arr) {
            Log.d("DEMO", "${pers.height} ${pers.weight} ${pers.age} ${pers.sex} ${pers.active} ${pers.maxCal}")
            if (pers.age != 0 && pers.height != 0 && pers.weight != 0 && pers.sex != 0 && pers.maxCal != 0.0)
                isPersonalDataFull = true
        }

        if (!isPersonalDataFull) {
            val text = "Write your personal data in 'Parameters'"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }



        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)

        recomlist.setAdapter(adapter)
    }
}
