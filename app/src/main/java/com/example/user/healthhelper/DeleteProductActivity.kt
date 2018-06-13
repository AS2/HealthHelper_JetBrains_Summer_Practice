package com.example.user.healthhelper

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_delete_product.*

class DeleteProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_product)

        val product_table = ProductDatabaseHandler(this)
        val data = mutableListOf<String>()

        val arr = product_table.FetchProducts("bread", true)
        for(items in arr) {
            data.add(items.prod_name)
        }

        var choosenProd : Int = 0
        var isChoose : Boolean = false

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        listdelete.adapter = adapter

        listdelete.setOnItemClickListener {
            parent, view, position, id ->

            var textView: TextView = view as TextView

            //view.setBackgroundColor(Color.parseColor("#ffaaaa"))
            deletebutton.setBackgroundColor(Color.parseColor("#ff4747"))
            deletebutton.text = "Delete " + "'${textView.text}'"
            isChoose = true
            choosenProd = position
        }

        deletebutton.setOnClickListener {
            if (isChoose) {
                isChoose = false
                product_table.RemoveProduct(arr[choosenProd].prodId)

                val intent_ap = Intent(this, AddProductActivity::class.java)
                startActivity(intent_ap)
            }
        }
    }
}
