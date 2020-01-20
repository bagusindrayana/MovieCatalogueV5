package com.bagus.moviecataloguev4.model.movie


import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

import kotlin.collections.ArrayList


@Parcelize
class MovieGenres(var id : Int,
                  var name : String,
                  var list_s: ArrayList<Movie> = arrayListOf<Movie>()
) : Parcelable



