package com.bagus.moviecataloguev5.db

import android.net.Uri
import android.provider.BaseColumns
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


internal class DatabaseContract {

    internal class FavoriteColumns : BaseColumns {

        companion object {
            const val AUTHORITY = "com.bagus.moviecataloguev5"
            const val SCHEME = "content"

            const val TABLE_NAME = "favorites"
            const val ID = "id"
            const val TITLE = "title"
            const val POSTER = "poster"
            const val ITEM_ID = "item_id"
            const val TYPE = "type"



            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }


    }
}