package com.example.database_practice

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class MyDatabase {
    object MyDBContract {
    object MyEntry: BaseColumns {
      const val TABLE_NAME = "myDBfile"
      const val rank = "rank"
      const val title = "title"
      const val artist = "artist"
      const val album = "album"
      const val num_like="num_like"
    }
  }
    class MyDbHelper(context: Context): SQLiteOpenHelper(context, DATABSE_NAME, null, DATABASE_VERSION) {
        val SQL_CREATE_ENTRIES=
            "CREATE TABLE ${MyDBContract.MyEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${MyDBContract.MyEntry.rank} INTEGER,"+
                    "${MyDBContract.MyEntry.title} TEXT," +
                    "${MyDBContract.MyEntry.artist} TEXT,"+
                    "${MyDBContract.MyEntry.album} TEXT,"+
                    "${MyDBContract.MyEntry.num_like} INTEGER"
        val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${MyDBContract.MyEntry.TABLE_NAME}"

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL(SQL_DELETE_ENTRIES)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }

        companion object {
            const val DATABASE_VERSION=1
            const val DATABSE_NAME = "myDBfile.db"
        }

        fun selectAll(): MutableList<MyElement> {
            val readList = mutableListOf<MyElement>()
            val db = readableDatabase
            val cursor = db.rawQuery("SELECT * FROM "+ MyDBContract.MyEntry.TABLE_NAME+",",null)
            with(cursor){
                while (moveToNext()){
                    readList.add(MyElement(
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                    ))
                }
            }
            cursor.close()
            db.close()
            return readList
        }
    }
}