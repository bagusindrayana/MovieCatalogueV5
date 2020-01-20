package com.bagus.moviecataloguev4.model.tv


import android.os.Parcel
import android.os.Parcelable
import com.bagus.moviecataloguev4.model.tv.Tv
import kotlinx.android.parcel.Parcelize

import kotlin.collections.ArrayList


@Parcelize
class TvGenres(var id : Int,
               var name : String,
               var list_s: ArrayList<Tv> = arrayListOf<Tv>()) : Parcelable
