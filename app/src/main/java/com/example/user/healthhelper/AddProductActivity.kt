package com.example.user.healthhelper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_add_product.*
import java.lang.reflect.Array

class AddProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val data = arrayOf("Coffee", "Tea")

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        productlist.adapter = adapter
    }
}
