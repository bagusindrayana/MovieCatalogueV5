package com.bagus.moviecataloguev4.model.tv


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Tv (var id : Int,
          var original_name : String,
          var name : String,
          var popularity : Float,
          var vote_count : Int,
          var first_air_date : String,
          var backdrop_path : String,
          var original_language : String,
          var vote_average : Float,
          var overview : String,
          var poster_path : String

): Parcelable