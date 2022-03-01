package com.example.contentproviderprojecta

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class DBHelper(
    context: Context,
    DB_FILE_NAME: String,
    DB_VERSION: Int
) : SQLiteOpenHelper(
    context,
    DB_FILE_NAME,
    null,
    DB_VERSION
) {

    private val TAG = "DBHelper"
    private val TABLE_NAME = "my_table"
    private val COLUMNS = arrayOf("content TEXT", "name TEXT", "num INTEGER")

    override fun onCreate(db: SQLiteDatabase?) {
        var sql = "CREATE TABLE if not exists $TABLE_NAME" +
                "( _id integer primary key autoincrement"
        for (column in COLUMNS) {
            sql += ", ${column}"
        }
        sql += " )"

        Log.e(TAG, "onCreate sql: $sql")

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        val sql = "DROP TABLE if exists $TABLE_NAME"
//
//        db?.execSQL(sql)
//        onCreate(db)
    }

    fun getItem() : ArrayList<ItemRow> {
        val list = ArrayList<ItemRow>()

        try{
            beginTransaction()
            val c = getAll(TABLE_NAME)
            c?.let {
                val total = c.getCount()
                if (total > 0) {
                    c.moveToFirst()
                    while (!c.isAfterLast()) {
                        val _id = c.getInt(0)
                        val content = c.getString(1)
                        val name = c.getString(2)
                        val num = c.getInt(3)

                        list.add(ItemRow(_id, content, name, num))
                        c.moveToNext()
                    }
                }
                c.close()
            }
        } catch (e: SQLiteException) {
            e.printStackTrace()
        } finally {
            endTransaction()
        }

        return list
    }

    fun setItem(content: String, name: String, num: Int) {
        val values = ContentValues()
        values.put("num", num)
        values.put("content", content)
        values.put("name", name)
        insert(TABLE_NAME, values)
    }

    fun setDelete() {
        AllDelete(TABLE_NAME)
    }

    protected fun getAll(tableName: String) : Cursor {
        return readableDatabase.query(tableName, null, null, null, null, null, null)
    }

    protected fun beginTransaction() {
        writableDatabase.beginTransaction()
    }

    protected fun endTransaction() {
        // db 속도 향상
        writableDatabase.setTransactionSuccessful()
        writableDatabase.endTransaction()
    }

    protected fun insert(tableName: String, values: ContentValues) {
        writableDatabase.insert(tableName, null, values)
    }

    protected fun AllDelete(tableName: String) {
        writableDatabase.delete(tableName, null, null)
    }
}