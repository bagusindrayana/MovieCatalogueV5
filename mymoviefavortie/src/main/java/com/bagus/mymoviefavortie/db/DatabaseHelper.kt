package com.bagus.mymoviefavorite.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "mcv5.db"

        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_NOTE = "CREATE TABLE ${DatabaseContract.FavoriteColumns.TABLE_NAME}" +
                " (${DatabaseContract.FavoriteColumns.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.FavoriteColumns.TITLE} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.POSTER} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.ITEM_ID} INTEGER NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.TYPE} TEXT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.FavoriteColumns.TABLE_NAME}")
        onCreate(db)
    }
}