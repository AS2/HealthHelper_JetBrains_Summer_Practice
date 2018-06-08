package com.example.user.healthhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class DatabaseHandler : SQLiteOpenHelper {


    companion object {

        val Tag = "DatabaseHandler"
        val DBName = "ProductListDB"
        val DBVersion = 1

        val prodId = "id"

        val tableName = "products"
        val productname = "pname"
        val productcalories = "pcalories"
        val productprotein = "pprotein"
        val productfats = "pfats"
        val productcarbohydrates = "pcarbohydrates"
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
                "("+ prodId + " INTEGER PRIMARY KEY,"  + productname + " TEXT, " + productcalories + " INTEGER, " + productprotein +
                " INTEGER," + productfats + " INTEGER," + productcarbohydrates  + " INTEGER );"

        p0!!.execSQL(sql1);
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        p0!!.execSQL("Drop table IF EXISTS " + tableName)
        onCreate(p0)

    }

    fun AddProduct(name: String, calories: Int, fats: Int, protein: Int, carbohydrates: Int): String {
        var pr = ContentValues()

        pr.put(DatabaseHandler.productname, name)
        pr.put(DatabaseHandler.productcalories, calories)
        pr.put(DatabaseHandler.productprotein, fats)
        pr.put(DatabaseHandler.productfats, protein)
        pr.put(DatabaseHandler.productcarbohydrates, carbohydrates)

        var Msg: String = "error";
        val ID = sqlObj!!.insert(tableName, "", pr)
        if (ID > 0) {
            Msg = "ok"
        }
        return Msg
    }

    fun FetchProducts(keyword: String, isAllProducts : Boolean): ArrayList<product> {

        var arraylist = ArrayList<product>()

        val sqb = SQLiteQueryBuilder()
        sqb.tables = tableName
        val cols = arrayOf(prodId, productname, productcalories, productprotein,
                productfats, productcarbohydrates)
        val rowSelArg = arrayOf(keyword)

        val cur : Cursor

        if (isAllProducts) {
            cur = sqb.query(sqlObj, cols, null, null, null, null, "pname")
        }
        else {
            cur  = sqb.query(sqlObj, cols, "pname like ?", rowSelArg, null, null, "pname")
        }



        if (cur.moveToFirst()) {

            do {
                val id = cur.getInt(cur.getColumnIndex(prodId))
                val name = cur.getString(cur.getColumnIndex(productname))
                val calories = cur.getInt(cur.getColumnIndex(productcalories))
                val protein = cur.getInt(cur.getColumnIndex(productprotein))
                val fats = cur.getInt(cur.getColumnIndex(productfats))
                val carbohydrates = cur.getInt(cur.getColumnIndex(productcarbohydrates))

                arraylist.add(product(name, calories, protein, fats, carbohydrates, id))

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