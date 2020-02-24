package com.bagus.moviecataloguev5.model.favorite

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite(
    var id: Int,
    var title: String,
    var poster: String,
    var item_id: Int,
    var type: String? = null
) : Parcelable