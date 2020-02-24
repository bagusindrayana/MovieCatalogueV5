package com.bagus.mymoviefavorite.db

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.bagus.mymoviefavorite.db.DatabaseContract.FavoriteColumns.Companion.AUTHORITY
import com.bagus.mymoviefavorite.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.bagus.mymoviefavorite.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME

class FavoriteProvider : ContentProvider() {

    companion object {
        private const val FAVORITE = 1
        private const val FAVORITE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var favoriteHelper: FavoriteHelper
        init {
            // content://com.dicoding.picodiploma.myfavoritesapp/favorite
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE)
            // content://com.dicoding.picodiploma.myfavoritesapp/favorite/id
            sUriMatcher.addURI(AUTHORITY,
                "$TABLE_NAME/#",
                FAVORITE_ID)
        }



    }
    
    override fun onCreate(): Boolean {
        favoriteHelper = FavoriteHelper.getInstance(context as Context)
        favoriteHelper.open()
        return true
    }
    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val cursor: Cursor?


        when (sUriMatcher.match(uri)) {
            FAVORITE -> cursor = selection?.let { favoriteHelper.queryFavoriteByType(it) }
            FAVORITE_ID -> cursor = uri.lastPathSegment?.let { favoriteHelper.queryById(it) }
            else -> cursor = null
        }

        //cursor?.setNotificationUris((context as Context).getContentResolver(),uri)
        return cursor
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        var added = 0
        when (FAVORITE) {
            sUriMatcher.match(uri) -> {
                favoriteHelper.insert(contentValues)
                added = 1
            }
            else -> {
                added = 0
            }
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }


    override fun update(uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<String>?): Int {
        val updated: Int = when (FAVORITE_ID) {
            sUriMatcher.match(uri) -> favoriteHelper.update(uri.lastPathSegment.toString(),contentValues)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {

        val deleted: Int = when (FAVORITE_ID) {
            sUriMatcher.match(uri) -> uri.lastPathSegment?.let { favoriteHelper.deleteById(it.toString()) }!!
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    override fun getType(p0: Uri): String? {
        return null
    }


}