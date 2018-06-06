package com.example.user.healthhelper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_parameters.*

class ParametersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parameters)

        val data_sex = arrayOf("Male", "Female")
        val adapter_sex = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data_sex)
        sex.setAdapter(adapter_sex)

        val data_ls = arrayOf("Lightly active", "Moderately active", "Very active", "Extra active")
        val adapter_ls = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data_ls)
        lifestyle.setAdapter(adapter_ls)
    }
}