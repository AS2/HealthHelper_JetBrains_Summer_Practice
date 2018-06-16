package com.example.user.healthhelper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_parameters.*
import java.sql.Types.NULL


class Persondata{
    var persId : Int = 0

    public var height: Int = 0
    public var weight: Int = 0
    public var age: Int = 0
    public var sex: Int = 0
    public var active: Double = 1.0
    public var maxCal: Double =  0.0

    constructor (Height : Int = 0, Weight : Int = 0, Age : Int = 0, Sex : Int = 0, Active : Double = 0.0, MaxCal : Double = 1.0, Id : Int = 0) {
        height = Height
        weight = Weight
        age = Age
        sex = Sex
        active = Active
        maxCal = MaxCal
        persId = Id
    }

}

class ParametersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parameters)


        val person_datalist = PersonDatabaseHandler(this)
        var personCount : Int = 0
        var arr = person_datalist.FetchProducts("bread", true)

        for (pers in arr) {
            Log.d("Demo", "${pers.height}, ${pers.weight}, ${pers.age}, ${pers.sex}, ${pers.active}, ${pers.maxCal}")
        }


        val data_sex = arrayOf("Male", "Female")
        val adapter_sex = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data_sex)
        sex.setAdapter(adapter_sex)
        val Person = Persondata()

        val data_ls = arrayOf("Lightly active", "Moderately active", "Very active", "Extra active")
        val adapter_ls = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data_ls)
        lifestyle.setAdapter(adapter_ls)

        save.setOnClickListener {
            Person.height = height.text.toString().toInt()
            Person.weight = weight.text.toString().toInt()
            Person.age = age.text.toString().toInt()
            when(lifestyle.selectedItem){
                "Lightly active" -> Person.active = 1.2
                "Moderately active" -> Person.active = 1.4
                "Very active" -> Person.active = 1.6
                "Extra active" -> Person.active = 1.8
            }

            when(sex.selectedItem){
                "Male" -> Person.sex = 5
                "Female" -> Person.sex = -161
            }
            Person.maxCal = (10 * Person.weight + 6.25 * Person.height - 5 * Person.age + Person.sex) * Person.active
            Log.d("demo",  "${Person.maxCal}")

            for (pers in arr) {
                person_datalist.RemoveProduct(pers.persId)
            }

            person_datalist.AddPerson(Person.height, Person.weight, Person.age, Person.sex, Person.active, Person.maxCal)

            val text = "Parameters have been saved!"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }

        returndp.setOnClickListener {
            Person.maxCal = 0.0
            Person.height = 0
            Person.weight = 0
            Person.age = 0
            Person.sex = 0
            Person.active = 1.0

            for (pers in arr) {
                person_datalist.RemoveProduct(pers.persId)
            }

            val text = "Parameters have been returned!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
    }
}