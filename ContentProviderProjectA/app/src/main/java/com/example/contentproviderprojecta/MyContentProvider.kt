package com.example.contentproviderprojecta

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

class MyContentProvider: ContentProvider() {

    private val TAG = "MyContentProvider"

    private lateinit var mDatabase: SQLiteDatabase

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(): Boolean {
        val dbHelper = DBHelper(requireContext(), "UserData.db", 1)
        mDatabase = dbHelper.writableDatabase

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {

        Log.e(TAG, "uri: $uri, projection: $projection, selection: $selection, selectionArgs: $selectionArgs, sortOrder: $sortOrder")
        val builder = SQLiteQueryBuilder()
        builder.tables = "my_table"
        builder.projectionMap = HashMap<String, String>()

        if (sortOrder?.isEmpty() == true) {
            sortOrder.plus("content")
        }

        val cursor = builder.query(mDatabase, projection, selection, selectionArgs, null, null, sortOrder)
        cursor.setNotificationUri(context?.contentResolver, uri)

        return cursor
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 0
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 0
    }

}