package com.bagus.moviecataloguev5.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.bagus.moviecataloguev5.db.DatabaseContract.FavoriteColumns.Companion.ID
import com.bagus.moviecataloguev5.db.DatabaseContract.FavoriteColumns.Companion.ITEM_ID
import com.bagus.moviecataloguev5.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.bagus.moviecataloguev5.db.DatabaseContract.FavoriteColumns.Companion.TYPE
import java.sql.SQLException

class FavoriteHelper (context: Context) {

    private lateinit var dataBaseHelper: DatabaseHelper
    private lateinit var database: SQLiteDatabase
    init {
        dataBaseHelper = DatabaseHelper(context)
    }
    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: FavoriteHelper? = null

        fun getInstance(context: Context): FavoriteHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = FavoriteHelper(context)
                    }
                }
            }
            return INSTANCE as FavoriteHelper
        }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$ID ASC",
            null)
    }

    fun queryFavoriteByType(t : String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$TYPE = ?",
            arrayOf(t),
            null,
            null,
            "$ID DESC",
            null)
    }

    fun queryById(id: String): Cursor {
        return database.query(DATABASE_TABLE, null, "$ITEM_ID = ?", arrayOf(id), null, null, null, null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$ID = '$id'", null)
    }
}