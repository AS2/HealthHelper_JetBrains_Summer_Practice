package com.example.user.healthhelper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_add_new_product.*
import kotlinx.android.synthetic.main.activity_eaten.*
import kotlinx.android.synthetic.main.activity_eaten.view.*
import java.text.SimpleDateFormat
import java.util.*

class totalproduct {
    val totalId : Int

    val total_name : String
    val total_calories : Double
    val total_fats : Double
    val total_protein : Double
    val total_carbonyhydrates : Double
    val total_time : Int

    constructor (name : String, calories : Double, fats : Double, protein : Double, carbohydrates : Double, time: Int, id: Int = 0)  {
        total_name = name
        total_calories = calories
        total_fats = fats
        total_protein = protein
        total_carbonyhydrates = carbohydrates
        totalId = id
        total_time = time
    }
}

class EatenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eaten)

    }

    override fun onResume() {
        super.onResume()

        val data = mutableListOf<String>()
        val getIntentfromCPA = intent

        val sdf = SimpleDateFormat("HH:mm")
        val currentDate = sdf.format(Date())

        val totalproduct_table = TotalDatabaseHandler(this)

        var tname : String = ""
        var tcalories : Double = .0
        var tfats : Double = .0
        var tprotein : Double = .0
        var tcarbonyhydrates : Double = .0

        var isMustBeAdded : Boolean = false

        var eatenCalories : Double = .0
        var eatenFats : Double = .0
        var eatenProtein : Double = .0
        var eatenCarbohydrates : Double = .0


        var mass : Int = 0

        if (getIntentfromCPA.hasExtra("totalname")) {
            tname = getIntentfromCPA.getStringExtra("totalname")
            isMustBeAdded = true
            Log.d("demo" ,"EatenToday take name!")
        }

        if (getIntentfromCPA.hasExtra("totalmass"))
            mass = getIntentfromCPA.getIntExtra("totalmass", 0)

        if (getIntentfromCPA.hasExtra("totalcalories"))
            tcalories = getIntentfromCPA.getDoubleExtra("totalcalories", .0) * mass.toDouble() / 100.0

        if (getIntentfromCPA.hasExtra("totalprotein"))
            tfats = getIntentfromCPA.getDoubleExtra("totalprotein", .0) * mass.toDouble() / 100.0

        if (getIntentfromCPA.hasExtra("totalfats"))
            tprotein = getIntentfromCPA.getDoubleExtra("totalfats", .0) * mass.toDouble() / 100.0

        if (getIntentfromCPA.hasExtra("totalcarbo"))
            tcarbonyhydrates = getIntentfromCPA.getDoubleExtra("totalcarbo", .0) * mass / 100.0


        val hoursAndMinutes = currentDate.split(":")
        val totalTime = hoursAndMinutes[0].toInt() * 60 + hoursAndMinutes[1].toInt()

        if (isMustBeAdded)
            totalproduct_table.AddTotalProduct(tname, tcalories, tfats, tprotein, tcarbonyhydrates, totalTime)




        val arr = totalproduct_table.FetchProducts("bread", true)

        for (prod in arr) {
            data.add("${prod.total_time / 60}:${prod.total_time - (prod.total_time / 60) * 60} - " + "${prod.total_name}")
            eatenCalories += prod.total_calories
            eatenCarbohydrates += prod.total_carbonyhydrates
            eatenFats += prod.total_fats
            eatenProtein += prod.total_protein
        }

        carbohydrates.text = "  • Carbohydrates: " + "${eatenCarbohydrates}"
        fats.text = "  • Fats: " + "${eatenFats}"
        calories.text = "  • Calories: " + "${eatenCalories}"
        protein.text = "  • Protein: " + "${eatenProtein}"


        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        rlayout.listeaten.setAdapter(adapter)
    }
}
