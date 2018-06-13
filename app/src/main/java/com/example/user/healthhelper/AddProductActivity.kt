package com.example.user.healthhelper

import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_add_product.*
import java.lang.reflect.Array

class product {
    val prodId : Int

    val prod_name : String
    val prod_calories : Int
    val prod_fats : Int
    val prod_protein : Int
    val prod_carbonyhydrates : Int

    constructor (name : String, calories : Int, fats : Int, protein : Int, carbohydrates : Int, id: Int = 0)  {
        prod_name = name
        prod_calories = calories
        prod_fats = fats
        prod_protein = protein
        prod_carbonyhydrates = carbohydrates
        prodId = id
    }
}



class AddProductActivity : AppCompatActivity() {

    var products = mutableListOf<product>()
    var products_filtered = mutableListOf<product>()
    var data = mutableListOf<String>()
    var data_filtered = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        var product_table = ProductDatabaseHandler(this)


        //println(product_table.AddProduct(pr))

//        for(items in arr) {
//            println(items.prod_name)
//        }


//        product_table.AddProduct("Coffee", 7, 1, 0, 0)
//        product_table.AddProduct("Tea", 28, 0, 0, 7)
//        product_table.AddProduct("Soup", 42, 2, 1, 5)
//        product_table.AddProduct("Porage", 105, 4, 3, 14)
//        product_table.AddProduct("Meat", 202, 13, 18, 4)
//        product_table.AddProduct("Cheese", 350, 26, 26, 0)
//        product_table.AddProduct("Yogurt", 62, 4, 3, 5)
//          product_table.AddProduct("Tide pods", 450, 0, 100, 100)
//        product_table.AddProduct("Apple", 37, 0, 0, 8)
//        product_table.AddProduct("Banana", 89, 0, 1, 21)
//        product_table.AddProduct("Bread", 212, 1, 7, 42)
//        product_table.AddProduct("Water", 0, 0, 0, 0)


//        val arr = product_table.FetchProducts("bread", true)
//        for(items in arr) {
//            product_table.RemoveProduct(items.prodId)
//        }

        val arr = product_table.FetchProducts("bread", true)
        for(items in arr) {
            println(items.prod_name)
        }

        for (prod in arr) {
            products.add(prod)
        }

        data_filtered.add("Add new product")
        data_filtered.add("Delete product")

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
            data_filtered.add("Delete product")

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
                0 -> {
                    val intent_anp = Intent(this, AddNewProductActivity::class.java)
                    startActivity(intent_anp)
                }
                1 -> {
                    val intent_anp = Intent(this, DeleteProductActivity::class.java)
                    startActivity(intent_anp)
                }
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
