package com.example.user.healthhelper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_parameters.*
import java.sql.Types.NULL


class Persondata{
    public var height: Int = 0
    public var weight: Int = 0
    public var age: Int = 0
    public var sex: Int = 0
    public var active: Double = 1.0
    public var maxCal: Double =  0.0

}

class ParametersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parameters)

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
                "male" -> Person.sex = 5
                "female" -> Person.sex = -161
            }
            Person.maxCal = (10 * Person.weight + 6.25 * Person.height - 5 * Person.age + Person.sex) * Person.active
            Log.d("demo",  "${Person.maxCal}")

        }
    }
}