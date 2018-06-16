package com.example.user.healthhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import com.example.user.healthhelper.R.id.calories
import com.example.user.healthhelper.R.id.fats

class RecomendationsDatabaseHandler : SQLiteOpenHelper {


    companion object {

        val Tag = "DatabaseHandler"
        val DBName = "RecomendationsDB"
        val DBVersion = 1

        val recId = "id"

        val tableName = "recomendations"
        val recTitle = "titel"
        val recContent = "content"
        val recTime = "time"
        val recChecked = "checkedflag"
    }

    var context: Context? = null
    var sqlObj: SQLiteDatabase

    constructor(context: Context) : super(context, DBName, null, DBVersion) {

        this.context = context;
        sqlObj = this.writableDatabase;
    }

    override fun onCreate(p0: SQLiteDatabase?) {

        //SQL for creating table
        var sql1: String = "CREATE TABLE IF NOT EXISTS " + tableName + " " +
                "("+ recId + " INTEGER PRIMARY KEY,"  + recTitle + " TEXT, " + recContent + " TEXT, " + recTime +
                " INTEGER," + recChecked + " INTEGER );"

        p0!!.execSQL(sql1);
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        p0!!.execSQL("Drop table IF EXISTS " + tableName)
        onCreate(p0)

    }

    fun AddTotalProduct(title: String, content : String, time : Int, isChecked : Int): String {
        val pr = ContentValues()

        pr.put(RecomendationsDatabaseHandler.recTitle, title)
        pr.put(RecomendationsDatabaseHandler.recContent, content)
        pr.put(RecomendationsDatabaseHandler.recTime, time)
        pr.put(RecomendationsDatabaseHandler.recChecked, isChecked)

        var Msg: String = "error";
        val ID = sqlObj!!.insert(tableName, "", pr)
        if (ID > 0) {
            Msg = "ok"
        }
        return Msg
    }

    fun FetchProducts(keyword: String, isAllProducts : Boolean): ArrayList<recomendation> {

        var arraylist = ArrayList<recomendation>()

        val sqb = SQLiteQueryBuilder()
        sqb.tables = tableName
        val cols = arrayOf(recId, recTitle, recContent, recTime, recChecked)
        val rowSelArg = arrayOf(keyword)

        val cur : Cursor

        if (isAllProducts) {
            cur = sqb.query(sqlObj, cols, null, null, null, null, "time")
        }
        else {
            cur  = sqb.query(sqlObj, cols, "title like ?", rowSelArg, null, null, "time")
        }



        if (cur.moveToFirst()) {
            do {
                val id = cur.getInt(cur.getColumnIndex(recId))
                val title = cur.getString(cur.getColumnIndex(recTitle))
                val content = cur.getString(cur.getColumnIndex(recContent))
                val time = cur.getInt(cur.getColumnIndex(recTime))
                val check = cur.getInt(cur.getColumnIndex(recChecked))

                arraylist.add(recomendation(title, content, id, time, check))

            } while (cur.moveToNext())
        }

        var count: Int = arraylist.size

        return arraylist
    }

    fun UpdateProduct(values: ContentValues, id: Int): String {

        var selectionArs = arrayOf(id.toString())

        val i = sqlObj!!.update(tableName, values, "id=?", selectionArs)
        if (i > 0) {
            return "ok";
        } else {

            return "error";
        }
    }

    fun RemoveProduct(id: Int): String {

        var selectionArs = arrayOf(id.toString())

        val i = sqlObj!!.delete(tableName, "id=?", selectionArs)
        if (i > 0) {
            return "ok";
        } else {

            return "error";
        }
    }
}