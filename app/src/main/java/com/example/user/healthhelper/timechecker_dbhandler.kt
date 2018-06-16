package com.example.user.healthhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class TimeCheckerDatabaseHandler : SQLiteOpenHelper {


    companion object {

        val Tag = "DatabaseHandler"
        val DBName = "timechecker"
        val DBVersion = 1

        val timeId = "id"

        val tableName = "lasttimeupdate"
        val lastTimeUpdate = "ltu"
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
                "("+ timeId + " INTEGER PRIMARY KEY,"  + lastTimeUpdate + " TEXT );"

        p0!!.execSQL(sql1);
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        p0!!.execSQL("Drop table IF EXISTS " + tableName)
        onCreate(p0)

    }

    fun AddNewTime(newTime : String): String {
        var pr = ContentValues()

        pr.put(TimeCheckerDatabaseHandler.lastTimeUpdate, newTime)

        var Msg: String = "error";
        val ID = sqlObj!!.insert(tableName, "", pr)
        if (ID > 0) {
            Msg = "ok"
        }
        return Msg
    }

    fun FetchProducts(keyword: String, isAllProducts : Boolean): ArrayList<timeCheckerClass> {

        var arraylist = ArrayList<timeCheckerClass>()

        val sqb = SQLiteQueryBuilder()
        sqb.tables = tableName
        val cols = arrayOf(timeId, lastTimeUpdate)
        val rowSelArg = arrayOf(keyword)

        val cur : Cursor

        if (isAllProducts) {
            cur = sqb.query(sqlObj, cols, null, null, null, null, "ltu")
        }
        else {
            cur  = sqb.query(sqlObj, cols, "ltu like ?", rowSelArg, null, null, "ltu")
        }



        if (cur.moveToFirst()) {

            do {
                val id = cur.getInt(cur.getColumnIndex(timeId))
                val name = cur.getString(cur.getColumnIndex(lastTimeUpdate))

                arraylist.add(timeCheckerClass(id, name))

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