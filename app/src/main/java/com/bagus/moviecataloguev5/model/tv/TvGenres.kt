package com.bagus.moviecataloguev5.model.tv


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

import kotlin.collections.ArrayList


@Parcelize
class TvGenres(var id : Int,
               var name : String,
               var list_s: ArrayList<Tv> = arrayListOf<Tv>()) : Parcelable
