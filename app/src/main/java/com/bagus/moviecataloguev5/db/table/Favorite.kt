package com.bagus.moviecataloguev5.db.table

import androidx.room.*


@Entity(tableName = "favorites")
class Favorite (

    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "poster")
    var poster: String,
    @ColumnInfo(name = "item_id")
    var item_id: Int,
    @ColumnInfo(name = "type")
    var type: String? = null



)