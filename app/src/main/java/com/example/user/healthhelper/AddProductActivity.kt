package com.example.user.healthhelper

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_add_product.*
import java.lang.reflect.Array

class product(name : String, calories : Int, fats : Int, protein : Int, carbohydrates : Int) {
    val prod_name : String = name
    val prod_calories : Int = calories
    val prod_fats : Int = fats
    val prod_protein : Int = protein
    val prod_carbonyhydrates : Int = carbohydrates
}

class AddProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        var products = mutableListOf<product>()
        var products_filtered = mutableListOf<product>()


        products.add(product("Coffee", 7, 1, 0, 0))
        products.add(product("Tea", 28, 0, 0, 7))
        products.add(product("Soup", 42, 2, 1, 5))
        products.add(product("Porage", 105, 4, 3, 14))
        products.add(product("Meat", 202, 13, 18, 4))
        products.add(product("Cheese", 350, 26, 26, 0))
        products.add(product("Yogurt", 62, 4, 3, 5))
        products.add(product("Apple", 37, 0, 0, 8))
        products.add(product("Banana", 89, 0, 1, 21))
        products.add(product("Bread", 212, 1, 7, 42))
        products.add(product("Water", 0, 0, 0, 0))


        var data = mutableListOf<String>()
        var data_filtered = mutableListOf<String>()

        data_filtered.add("Add new product")
        data_filtered.add("Deleted product")

        for (prod in products) {
            data.add(prod.prod_name)
            data_filtered.add(prod.prod_name)
            products_filtered.add(prod)
        }


        searchbutton.setOnClickListener {
            val text : String = searchproducts.text.toString().toLowerCase()

            data_filtered.clear()
            products_filtered.clear()
            if (text.length == 0)
            {
                for (prod in products)
                    products_filtered.add(prod)
            }
            else {
                for (p in products) {
                    var name: String = p.prod_name.toLowerCase()
                    if (name.contains(text)) {
                        products_filtered.add(p)
                    }
                }
            }


            data_filtered.clear()

            data_filtered.add("Add new product")
            data_filtered.add("Deleted product")

            for(p in products_filtered)
                data_filtered.add(p.prod_name)


            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data_filtered)
            productlist.adapter = adapter
        }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data_filtered)
        productlist.adapter = adapter

        productlist.setOnItemClickListener {
            parent, view, position ,id ->
            val intent_currentprod = Intent(this, CurrentProductActivity::class.java)

            when (position) {
                0 -> Log.d("demo", "Add new product")
                1 -> Log.d("demo", "Delete product")
                else -> {
                    intent_currentprod.putExtra("name", products_filtered[position - 2].prod_name)
                    intent_currentprod.putExtra("protein", products_filtered[position - 2].prod_protein)
                    intent_currentprod.putExtra("fats", products_filtered[position - 2].prod_fats)
                    intent_currentprod.putExtra("carbohydrates", products_filtered[position - 2].prod_carbonyhydrates)
                    intent_currentprod.putExtra("calories",products_filtered[position - 2].prod_calories)
                    startActivity(intent_currentprod)
                }
            }
        }
    }
}
