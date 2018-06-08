package com.example.user.healthhelper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_eaten.*
import kotlinx.android.synthetic.main.activity_eaten.view.*
import java.text.SimpleDateFormat
import java.util.*

class EatenActivity : AppCompatActivity() {

    val data = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eaten)

    }

    override fun onResume() {
        super.onResume()

        var getIntentfromCPA = intent

        val sdf = SimpleDateFormat("hh:mm")
        val currentDate = sdf.format(Date())

        if (getIntentfromCPA.hasExtra("totalname")) {
            data.add(currentDate + " - " + getIntentfromCPA.getStringExtra("totalname"))
            Log.d("demo" ,"EatenToday take name!")
        }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        rlayout.listeaten.setAdapter(adapter)
    }
}
