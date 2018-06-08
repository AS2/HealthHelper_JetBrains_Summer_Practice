package com.example.user.healthhelper

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_new_product.*

class AddNewProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_product)

        addbutton.setOnClickListener {
            var product_table = DatabaseHandler(this)
            product_table.AddProduct(productname.text.toString(), productcalories.text.toString().toInt(),
                    productfats.text.toString().toInt(), productprotein.text.toString().toInt(), productcarbohydrates.text.toString().toInt())

            val text = "New product has been added!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()

            val intent_ap = Intent(this, AddProductActivity::class.java)
            startActivity(intent_ap)
        }
    }
}
