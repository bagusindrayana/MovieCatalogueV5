package com.bagus.mymoviefavortie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite(
    var id: Int,
    var title: String,
    var poster: String,
    var item_id: Int,
    var type: String? = null
) : Parcelable