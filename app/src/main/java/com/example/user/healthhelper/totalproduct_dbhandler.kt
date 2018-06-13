package com.example.user.healthhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class TotalDatabaseHandler : SQLiteOpenHelper {


    companion object {

        val Tag = "DatabaseHandler"
        val DBName = "TotalProductDB"
        val DBVersion = 1

        val totalId = "id"

        val tableName = "total"
        val totalname = "tname"
        val totalcalories = "tcalories"
        val totalprotein = "tprotein"
        val totalfats = "tfats"
        val totalcarbohydrates = "tcarbohydrates"
        val totaltime = "ttime"
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
                "("+ totalId + " INTEGER PRIMARY KEY,"  + totalname + " TEXT, " + totalcalories + " REAL, " + totalprotein +
                " REAL," + totalfats + " REAL," + totalcarbohydrates + " REAL," + totaltime  + " INTEGER );"

        p0!!.execSQL(sql1);
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        p0!!.execSQL("Drop table IF EXISTS " + tableName)
        onCreate(p0)

    }

    fun AddTotalProduct(name: String, calories: Double, fats: Double, protein: Double, carbohydrates: Double, tottime : Int): String {
        val pr = ContentValues()

        pr.put(TotalDatabaseHandler.totalname, name)
        pr.put(TotalDatabaseHandler.totalcalories, calories)
        pr.put(TotalDatabaseHandler.totalprotein, fats)
        pr.put(TotalDatabaseHandler.totalfats, protein)
        pr.put(TotalDatabaseHandler.totalcarbohydrates, carbohydrates)
        pr.put(TotalDatabaseHandler.totaltime, tottime)

        var Msg: String = "error";
        val ID = sqlObj!!.insert(tableName, "", pr)
        if (ID > 0) {
            Msg = "ok"
        }
        return Msg
    }

    fun FetchProducts(keyword: String, isAllProducts : Boolean): ArrayList<totalproduct> {

        var arraylist = ArrayList<totalproduct>()

        val sqb = SQLiteQueryBuilder()
        sqb.tables = tableName
        val cols = arrayOf(totalId, totalname, totalcalories, totalprotein,
                totalfats, totalcarbohydrates, totaltime)
        val rowSelArg = arrayOf(keyword)

        val cur : Cursor

        if (isAllProducts) {
            cur = sqb.query(sqlObj, cols, null, null, null, null, "ttime")
        }
        else {
            cur  = sqb.query(sqlObj, cols, "tname like ?", rowSelArg, null, null, "ttime")
        }



        if (cur.moveToFirst()) {

            do {
                val id = cur.getInt(cur.getColumnIndex(totalId))
                val name = cur.getString(cur.getColumnIndex(totalname))
                val calories = cur.getDouble(cur.getColumnIndex(totalcalories))
                val protein = cur.getDouble(cur.getColumnIndex(totalprotein))
                val fats = cur.getDouble(cur.getColumnIndex(totalfats))
                val carbohydrates = cur.getDouble(cur.getColumnIndex(totalcarbohydrates))
                val time = cur.getInt(cur.getColumnIndex(totaltime))

                arraylist.add(totalproduct(name, calories, protein, fats, carbohydrates, time, id))

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