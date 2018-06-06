package com.example.user.healthhelper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
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

        var data = Array<String>(100,
                {i ->
                    if (i % 2 == 0)
                        "kek"
                    else
                        "lol"})

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)

        recomlist.setAdapter(adapter)
    }
}
