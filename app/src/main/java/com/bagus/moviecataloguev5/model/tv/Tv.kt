package com.bagus.moviecataloguev5.model.tv


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Tv (var id : Int,
          var name : String,
          var poster_path : String,
          var vote_average : String? = null,
          var first_air_date : String? = null
): Parcelable