package com.example.user.healthhelper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_eaten.*
import kotlinx.android.synthetic.main.activity_eaten.view.*

class EatenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eaten)

        val data = arrayOf("12:05 - sandwich", "12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich","12:05 - sandwich")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)

        rlayout.listeaten.setAdapter(adapter)
    }
}
