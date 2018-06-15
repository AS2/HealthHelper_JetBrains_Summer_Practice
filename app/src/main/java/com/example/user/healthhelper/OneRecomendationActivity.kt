package com.example.user.healthhelper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_current_product.*
import kotlinx.android.synthetic.main.activity_one_recomendation.*

class OneRecomendationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_recomendation)

        val getIntentFromRecomendationActivity = intent
        var id : Int = 0

        if (getIntentFromRecomendationActivity.hasExtra("Id"))
            id = getIntentFromRecomendationActivity.getIntExtra("Id", 0)

        val rec_dblist = RecomendationsDatabaseHandler(this)
        val rec_arr = rec_dblist.FetchProducts("bread", true)

        var true_rec = recomendation("", "", 0, 0, 0)

        for (rec in rec_arr) {
            if (rec.Id == id)
                true_rec = rec
        }

        val ContentBlocks = true_rec.getContent().split(";", ",")

        rtitle.text = true_rec.getTitle()

        rdate.text = "${true_rec.Time / 365 + 2000} year, ${true_rec.Time - true_rec.Time / 365 * 365} day"
        rcontent.text = " ${ContentBlocks[0]}"
        rexmpl1.text = "        ${ContentBlocks[1]}"
        rexmpl2.text = "        ${ContentBlocks[2]}"
        rexmpl3.text = "        ${ContentBlocks[3]}"
        rexmpl4.text = "        ${ContentBlocks[4]}"

    }
}
