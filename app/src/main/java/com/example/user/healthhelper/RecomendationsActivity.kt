package com.example.user.healthhelper

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_recomendations.*
import java.text.SimpleDateFormat
import java.util.*

class recomendation (title : String, content : String, id : Int, time : Int, check : Int = 0) {
    private val Header = title
    private val Content = content
    var IsChecked = check
    var Id = id
    var Time = time


    fun getTitle() : String {
        return Header
    }

    fun getContent() : String {
        return Content
    }

    fun checked() {
        IsChecked = 1
    }
}

class RecomendationsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendations)
        val data = mutableListOf<String>()


        var eatenproducts_list = TotalDatabaseHandler(this)
        var products_list = ProductDatabaseHandler(this)
        val params_list = PersonDatabaseHandler(this)
        var isPersonalDataFull : Boolean = false

        var eatenCalories : Double = .0
        var eatenFats : Double = .0
        var eatenProtein : Double = .0
        var eatenCarbohydrates : Double = .0

        val products_arr = products_list.FetchProducts("Bread", true)

        val persondata_arr = params_list.FetchProducts("bread", true)
        for (pers in persondata_arr) {
            if (pers.age != 0 && pers.height != 0 && pers.weight != 0 && pers.sex != 0 && pers.maxCal != 0.0)
                isPersonalDataFull = true
        }
        if (!isPersonalDataFull) {
            val text = "Write your personal data in 'Parameters'"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }

        val total_arr = eatenproducts_list.FetchProducts("bread", true)
        for (prod in total_arr) {
            eatenCalories += prod.total_calories
            eatenCarbohydrates += prod.total_carbonyhydrates
            eatenFats += prod.total_fats
            eatenProtein += prod.total_protein
        }


        var recomendations = mutableListOf<recomendation>()
        val rec_dblist = RecomendationsDatabaseHandler(this)
        var recom_arr = rec_dblist.FetchProducts("Bread", true)

        val sdf = SimpleDateFormat("yy:DD")
        val currentDate = sdf.format(Date())

        for (rec in recom_arr) {
            rec_dblist.RemoveProduct(rec.Id)
        }


        if (isPersonalDataFull && eatenCalories != 0.0) {
            val maxFats : Double = (persondata_arr[0].maxCal * 0.2) / 9
            val maxProtein : Double = (persondata_arr[0].maxCal * 0.4) / 4
            val maxCarbohydrates : Double = (persondata_arr[0].maxCal * 0.4) / 4

            var tooFat = totalproduct("", .0, .0, .0, .0, 0)

            if (eatenFats < maxFats * 0.8) {
                rec_dblist.AddTotalProduct("Too little fats",
                        "You eat too few fats. We recommended you to eat more fat food or include in your died products like:; " +
                                "cheese, eggs, fish, beef",
                        currentDate.split(":")[0].toInt() * 365 + currentDate.split(":")[1].toInt(), 0)
            }

            if (eatenFats > maxFats * 1.2) {
                for (total in total_arr) {
                    if (total.total_fats > tooFat.total_fats)
                        tooFat = total
                }

                rec_dblist.AddTotalProduct("Too much fats",
                        "You eat too much fats. We recommended you to eat less fat food or exclude from your died products like:; " +
                                "${tooFat.total_name}, , , ",
                        currentDate.split(":")[0].toInt() * 365 + currentDate.split(":")[1].toInt(), 0)
            }

            if (eatenProtein < maxProtein * 0.8) {
                rec_dblist.AddTotalProduct("Too little protein",
                        "You eat to few protein. We recommended you to eat more food includes much protein or include in your died products like:; " +
                                "fish, chicken, kidney beans, mozzarella",
                        currentDate.split(":")[0].toInt() * 365 + currentDate.split(":")[1].toInt(), 0)
            }

            if (eatenProtein > maxProtein * 1.2) {
                for (total in total_arr) {
                    if (total.total_protein > tooFat.total_protein)
                        tooFat = total
                }

                rec_dblist.AddTotalProduct("Too much protein",
                        "You eat to much protein. We recommended you to eat less food witch much protein or exclude from your died products like:; " +
                                "${tooFat.total_name}, , , ",
                        currentDate.split(":")[0].toInt() * 365 + currentDate.split(":")[1].toInt(), 0)
            }

            if (eatenCarbohydrates < maxCarbohydrates * 0.8) {
                rec_dblist.AddTotalProduct("Too little carbohydrates",
                        "You eat to few carbohydrates. We recommended you to eat more food with much carbohydrates or include in your died products like:; " +
                                "rice, spaghetti, bread, roasted potato",
                        currentDate.split(":")[0].toInt() * 365 + currentDate.split(":")[1].toInt(), 0)
            }

            if (eatenCarbohydrates > maxCarbohydrates * 1.2) {
                for (total in total_arr) {
                    if (total.total_carbonyhydrates > tooFat.total_carbonyhydrates)
                        tooFat = total
                }

                rec_dblist.AddTotalProduct("Too much carbohydrates",
                        "You eat to much carbohydrates. We recommended you to eat less food with much carbohydrates or exclude from your died products like:; " +
                                "${tooFat.total_name}, , , ",
                        currentDate.split(":")[0].toInt() * 365 + currentDate.split(":")[1].toInt(), 0)
            }

            if (eatenCalories < persondata_arr[0].maxCal * 0.8) {
                rec_dblist.AddTotalProduct("Too little calories",
                        "You eat to few calories. We recommended you to eat more food with much calories or include in your died products like:; " +
                                "cacao, nut, smoked sausage, sugar",
                        currentDate.split(":")[0].toInt() * 365 + currentDate.split(":")[1].toInt(), 0)
            }

            if (eatenCalories > persondata_arr[0].maxCal * 1.2) {
                for (total in total_arr) {
                    if (total.total_calories > tooFat.total_calories)
                        tooFat = total
                }

                rec_dblist.AddTotalProduct("Too much calories",
                        "You eat to much calories. We recommended you to eat less food with calories or exclude from your died products like:; " +
                                "${tooFat.total_name}, , , ",
                        currentDate.split(":")[0].toInt() * 365 + currentDate.split(":")[1].toInt(), 0)
            }
        }

        recom_arr = rec_dblist.FetchProducts("Bread", true)
        for (rec in recom_arr) {
            data.add("${SimpleDateFormat("dd:MM:yyyy").format(Date())} - " + rec.getTitle())
        }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)

        recomlist.setAdapter(adapter)

        recomlist.setOnItemClickListener {
            parent, view, position, id ->

            var intent_or = Intent(this, OneRecomendationActivity::class.java)

            intent_or.putExtra("Id", recom_arr[position].Id)
            startActivity(intent_or)
        }
    }
}
