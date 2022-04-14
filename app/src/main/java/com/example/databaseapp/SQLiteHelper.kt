package com.example.databaseapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.text.Editable
import java.lang.Exception

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null,DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val  DATABASE_NAME = "hostel.db"
        private const val TBL_HOSTEL = "tbl_hostel"
        private const val ID = "id"
        private const val NAME = "name"
        private const val STARS = "stars"
        private const val ESTIMATION = "estimation"
        private const val NUMBER = "number"
    }

    //Функция создания таблицы
    override fun onCreate(db: SQLiteDatabase?) {
        val createTbHostel = ("CREATE TABLE " + TBL_HOSTEL + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT NOT NULL UNIQUE, "
                + STARS + " INTEGER NOT NULL CHECK(stars > 0 AND stars < 6), "
                + ESTIMATION + " DOUBLE, "
                + NUMBER + " INTEGER NOT NULL" + ")")
        db?.execSQL(createTbHostel)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_HOSTEL")
        onCreate(db)
    }

    //Функция вставки в таблицу
    fun insertHostel(hl: HostelModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        //contentValues.put(ID, hl.id)
        contentValues.put(NAME, hl.name)
        contentValues.put(STARS, hl.stars)
        contentValues.put(ESTIMATION, hl.estimation)
        contentValues.put(NUMBER, hl.number)

        val success = db.insert(TBL_HOSTEL, null, contentValues)
        db.close()
        return success
    }

    fun getAllHostel():ArrayList<HostelModel>{
        val hlList: ArrayList<HostelModel> = ArrayList()
        val selectQuerry = "SELECT * FROM $TBL_HOSTEL"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuerry, null)
        }catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuerry)
            return ArrayList()
        }
        var id:Int
        var name: String
        var stars: Int
        var estimation: Double
        var number: Int

        if(cursor.moveToFirst()){
            do {
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                stars = cursor.getInt(cursor.getColumnIndexOrThrow("stars"))
                estimation = cursor.getDouble(cursor.getColumnIndexOrThrow("estimation"))
                number = cursor.getInt(cursor.getColumnIndexOrThrow("number"))
                val hl = HostelModel(name = name, stars = stars, estimation = estimation, number = number)
                hlList.add(hl)
            }while (cursor.moveToNext())
        }
        return hlList
    }

    fun getHostelNumberMaxMin():ArrayList<HostelModel>{
        val hlList: ArrayList<HostelModel> = ArrayList()
        val selectQuerry = "SELECT * FROM $TBL_HOSTEL ORDER BY number DESC"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuerry, null)
        }catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuerry)
            return ArrayList()
        }
        var id:Int
        var name: String
        var stars: Int
        var estimation: Double
        var number: Int

        if(cursor.moveToFirst()){
            do {
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                stars = cursor.getInt(cursor.getColumnIndexOrThrow("stars"))
                estimation = cursor.getDouble(cursor.getColumnIndexOrThrow("estimation"))
                number = cursor.getInt(cursor.getColumnIndexOrThrow("number"))
                val hl = HostelModel(name = name, stars = stars, estimation = estimation, number = number)
                hlList.add(hl)
            }while (cursor.moveToNext())
        }
        return hlList
    }

    fun getHostelEstNumb():ArrayList<HostelModel>{
        val hlList: ArrayList<HostelModel> = ArrayList()
        val selectQuerry = "SELECT * FROM $TBL_HOSTEL WHERE number > 200 and estimation > 8.3"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuerry, null)
        }catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuerry)
            return ArrayList()
        }
        var id:Int
        var name: String
        var stars: Int
        var estimation: Double
        var number: Int

        if(cursor.moveToFirst()){
            do {
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                stars = cursor.getInt(cursor.getColumnIndexOrThrow("stars"))
                estimation = cursor.getDouble(cursor.getColumnIndexOrThrow("estimation"))
                number = cursor.getInt(cursor.getColumnIndexOrThrow("number"))
                val hl = HostelModel(name = name, stars = stars, estimation = estimation, number = number)
                hlList.add(hl)
            }while (cursor.moveToNext())
        }
        return hlList
    }

    fun getHostelStars(star: Int):ArrayList<HostelModel>{
        val hlList: ArrayList<HostelModel> = ArrayList()
        val selectQuerry = "SELECT * FROM $TBL_HOSTEL WHERE stars = $star"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuerry, null)
        }catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuerry)
            return ArrayList()
        }
        var id:Int
        var name: String
        var stars: Int
        var estimation: Double
        var number: Int

        if(cursor.moveToFirst()){
            do {
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                stars = cursor.getInt(cursor.getColumnIndexOrThrow("stars"))
                estimation = cursor.getDouble(cursor.getColumnIndexOrThrow("estimation"))
                number = cursor.getInt(cursor.getColumnIndexOrThrow("number"))
                val hl = HostelModel(name = name, stars = stars, estimation = estimation, number = number)
                hlList.add(hl)
            }while (cursor.moveToNext())
        }
        return hlList
    }

    fun getHostelAlf():ArrayList<HostelModel>{
        val hlList: ArrayList<HostelModel> = ArrayList()
        val selectQuerry = "SELECT * FROM $TBL_HOSTEL ORDER BY name"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuerry, null)
        }catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuerry)
            return ArrayList()
        }
        var id:Int
        var name: String
        var stars: Int
        var estimation: Double
        var number: Int

        if(cursor.moveToFirst()){
            do {
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                stars = cursor.getInt(cursor.getColumnIndexOrThrow("stars"))
                estimation = cursor.getDouble(cursor.getColumnIndexOrThrow("estimation"))
                number = cursor.getInt(cursor.getColumnIndexOrThrow("number"))
                val hl = HostelModel(name = name, stars = stars, estimation = estimation, number = number)
                hlList.add(hl)
            }while (cursor.moveToNext())
        }
        return hlList
    }

    fun deleteHostelBuName(name: String): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(NAME, name)

        val success = db.delete(TBL_HOSTEL, "name = '$name'", null)
        db.close()
        return success
    }

    fun getIdHostel(name: String): ArrayList<Int> {
            var HostelArrayList = ArrayList<Int>()
            val selectQuery = "SELECT id FROM $TBL_HOSTEL WHERE name = '$name'"
            val db = this.readableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    HostelArrayList.add(cursor.getInt(cursor.getColumnIndexOrThrow("id")))
                } while (cursor.moveToNext())
            }
        return HostelArrayList
        }

    fun updateHostel(hl:HostelModel, id: Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(NAME, hl.name)
        contentValues.put(STARS, hl.stars)
        contentValues.put(ESTIMATION, hl.estimation)
        contentValues.put(NUMBER, hl.number)

        val success = db.update(TBL_HOSTEL, contentValues,"id = $id", null)
        db.close()
        return success
    }

    fun getHostelName(name: String):ArrayList<HostelModel>{
        val hlList: ArrayList<HostelModel> = ArrayList()
        val selectQuerry = "SELECT * FROM $TBL_HOSTEL WHERE name LIKE '%$name%'"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuerry, null)
        }catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuerry)
            return ArrayList()
        }
        var id:Int
        var name: String
        var stars: Int
        var estimation: Double
        var number: Int

        if(cursor.moveToFirst()){
            do {
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                stars = cursor.getInt(cursor.getColumnIndexOrThrow("stars"))
                estimation = cursor.getDouble(cursor.getColumnIndexOrThrow("estimation"))
                number = cursor.getInt(cursor.getColumnIndexOrThrow("number"))
                val hl = HostelModel(name = name, stars = stars, estimation = estimation, number = number)
                hlList.add(hl)
            }while (cursor.moveToNext())
        }
        return hlList
    }

}