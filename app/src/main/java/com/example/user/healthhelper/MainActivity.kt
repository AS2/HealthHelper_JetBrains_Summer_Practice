package com.example.user.healthhelper

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val serviceintent = Intent(this, MyService::class.java)
        //startService(serviceintent)

        add.setOnClickListener {
            val intent_add = Intent(this, AddProductActivity::class.java)

            startActivity(intent_add)
        }

        eaten.setOnClickListener {
            val intent_teaten = Intent(this, EatenActivity::class.java)

            startActivity(intent_teaten)
        }

        recomendations.setOnClickListener {
            val intent_rec = Intent(this, RecomendationsActivity::class.java)

            startActivity(intent_rec)
        }

        params.setOnClickListener {
            val intent_params = Intent(this, ParametersActivity::class.java)

            startActivity(intent_params)
        }

    }
}
