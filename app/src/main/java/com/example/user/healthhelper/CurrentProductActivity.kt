package com.example.user.healthhelper

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_current_product.*
import kotlinx.android.synthetic.main.activity_eaten.*

class CurrentProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_product)

        var getIntentFromAPA = intent

        var Calories : Double = 0.0
        var Fats : Double = 0.0
        var Protein : Double = 0.0
        var Carbohydrates : Double = 0.0
        var isSended : Boolean = false

        if (getIntentFromAPA.hasExtra("name"))
            infotitle.text = getIntentFromAPA.getStringExtra("name")
        if (getIntentFromAPA.hasExtra("protein"))
            Protein = (infomass.text.toString().toDouble() / 100) * getIntentFromAPA.getIntExtra("protein", 0)
        if (getIntentFromAPA.hasExtra("fats"))
            Fats = (infomass.text.toString().toDouble() / 100) *  getIntentFromAPA.getIntExtra("fats", 0)
        if (getIntentFromAPA.hasExtra("carbohydrates"))
            Carbohydrates = (infomass.text.toString().toDouble() / 100) * getIntentFromAPA.getIntExtra("carbohydrates", 0)
        if (getIntentFromAPA.hasExtra("calories"))
            Calories = (infomass.text.toString().toDouble() / 100) *  getIntentFromAPA.getIntExtra("calories", 0)

        infocalories.text = "         • Calories: ${Calories}"
        infofats.text = "         • Fats: ${Fats}"
        infocarbo.text = "         • Carbohydrates: ${Carbohydrates}"
        infoprotein.text = "         • Protein: ${Protein}"


        infocalc.setOnClickListener {
            if (getIntentFromAPA.hasExtra("name"))
                infotitle.text = getIntentFromAPA.getStringExtra("name")
            if (getIntentFromAPA.hasExtra("protein"))
                Protein = (infomass.text.toString().toDouble() / 100) * getIntentFromAPA.getIntExtra("protein", 0)
            if (getIntentFromAPA.hasExtra("fats"))
                Fats = (infomass.text.toString().toDouble() / 100) *  getIntentFromAPA.getIntExtra("fats", 0)
            if (getIntentFromAPA.hasExtra("carbohydrates"))
                Carbohydrates = (infomass.text.toString().toDouble() / 100) * getIntentFromAPA.getIntExtra("carbohydrates", 0)
            if (getIntentFromAPA.hasExtra("calories"))
                Calories = (infomass.text.toString().toDouble() / 100) *  getIntentFromAPA.getIntExtra("calories", 0)

            infocalories.text = "         • Calories: ${Calories}"
            infofats.text = "         • Fats: ${Fats}"
            infocarbo.text = "         • Carbohydrates: ${Carbohydrates}"
            infoprotein.text = "         • Protein: ${Protein}"
        }

        infobutton.setOnClickListener {
            isSended = false
            val intent_te = Intent(this, EatenActivity::class.java)

            intent_te.putExtra("totalname", infotitle.text)
            intent_te.putExtra("totalprotein", Protein)
            intent_te.putExtra("totalcarbo", Carbohydrates)
            intent_te.putExtra("totalfats", Fats)
            intent_te.putExtra("totalcalories", Calories)
            intent_te.putExtra("totalmass", infomass.text.toString().toInt())
            Log.d("demo" ,"CurrentProduct send name!")
            startActivity(intent_te)
            isSended = true
        }
    }
}
