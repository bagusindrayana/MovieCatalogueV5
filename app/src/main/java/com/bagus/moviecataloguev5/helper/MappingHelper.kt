package com.bagus.moviecataloguev5.helper

import android.database.Cursor
import com.bagus.moviecataloguev5.db.DatabaseContract
import com.bagus.moviecataloguev5.model.favorite.Favorite

import com.bagus.moviecataloguev5.db.table.Favorite as TB_FAV

object MappingHelper {

    fun mapCursorToArrayList(favoritesCursor: Cursor?): ArrayList<Favorite> {
        val favoritesList = ArrayList<Favorite>()

        favoritesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE))
                val poster = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.POSTER))
                val item_id = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ITEM_ID))
                val type = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TYPE))
                favoritesList.add(Favorite(id, title, poster, item_id.toInt(),type))
            }
        }
        return favoritesList
    }

    fun mapCursorToObject(favoritesCursor: Cursor?): Favorite? {
        var favorite : Favorite? = null
        favoritesCursor?.apply {
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ID))
            val title = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE))
            val poster = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.POSTER))
            val item_id = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ITEM_ID))
            val type = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TYPE))
            favorite = Favorite(id, title, poster, item_id.toInt(),type)
        }
        return favorite
    }

    fun mapCursorToObject2(favoritesCursor: Cursor?): TB_FAV {
        var favorite : TB_FAV = TB_FAV(0,"-","-",0,"-")
        favoritesCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ID))
            val title = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE))
            val poster = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.POSTER))
            val item_id = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ITEM_ID))
            val type = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TYPE))
            favorite = TB_FAV(id, title, poster, item_id.toInt(),type)
        }
        return favorite
    }
}