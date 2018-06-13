package com.example.user.healthhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class PersonDatabaseHandler : SQLiteOpenHelper {


    companion object {
        val Tag = "DatabaseHandler"
        val DBName = "PersonProductDB"
        val DBVersion = 1

        val persId = "id"

        val tableName = "person_param"
        val persHeight = "pheight"
        val persWeight = "pweight"
        val persAge = "page"
        val persSex = "psex"
        val persActive = "pactive"
        val persMaxCal = "pmaxCal"
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
                "("+ persId + " INTEGER PRIMARY KEY,"  + persHeight + " INTEGER, " + persWeight + " INTEGER, " + persAge +
                " INTEGER," + persSex + " INTEGER," + persActive + " REAL," + persMaxCal  + " REAL );"

        p0!!.execSQL(sql1);
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        p0!!.execSQL("Drop table IF EXISTS " + tableName)
        onCreate(p0)

    }

    fun AddPerson(Height: Int, Weight: Int, Age : Int, Sex : Int, Active : Double, MaxCal : Double): String {
        val pr = ContentValues()

        pr.put(TotalDatabaseHandler.totalname, Height)
        pr.put(TotalDatabaseHandler.totalcalories, Weight)
        pr.put(TotalDatabaseHandler.totalprotein, Age)
        pr.put(TotalDatabaseHandler.totalfats, Sex)
        pr.put(TotalDatabaseHandler.totalcarbohydrates, Active)
        pr.put(TotalDatabaseHandler.totaltime, MaxCal)

        var Msg: String = "error";
        val ID = sqlObj!!.insert(tableName, "", pr)
        if (ID > 0) {
            Msg = "ok"
        }
        return Msg
    }

    fun FetchProducts(keyword: String, isAllProducts : Boolean): ArrayList<Persondata> {

        var arraylist = ArrayList<Persondata>()

        val sqb = SQLiteQueryBuilder()
        sqb.tables = tableName
        val cols = arrayOf(persId, persHeight, persWeight, persAge,
                persSex, persActive, persMaxCal)
        val rowSelArg = arrayOf(keyword)

        val cur : Cursor

        if (isAllProducts) {
            cur = sqb.query(sqlObj, cols, null, null, null, null, "pheight")
        }
        else {
            cur  = sqb.query(sqlObj, cols, "pheight like ?", rowSelArg, null, null, "pheight")
        }



        if (cur.moveToFirst()) {

            do {
                val id = cur.getInt(cur.getColumnIndex(persId))
                val hght = cur.getInt(cur.getColumnIndex(persHeight))
                val wght = cur.getInt(cur.getColumnIndex(persWeight))
                val age = cur.getInt(cur.getColumnIndex(persAge))
                val sex = cur.getInt(cur.getColumnIndex(persSex))
                val active = cur.getDouble(cur.getColumnIndex(persActive))
                val maxcal = cur.getDouble(cur.getColumnIndex(persMaxCal))

                arraylist.add(Persondata(hght, wght, age, sex, active, maxcal, id))

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