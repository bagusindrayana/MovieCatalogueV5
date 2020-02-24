package com.bagus.moviecataloguev5.model.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieGenresResponse(var genres : ArrayList<MovieGenres>): Parcelable