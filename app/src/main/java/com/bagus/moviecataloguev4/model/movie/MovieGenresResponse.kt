package com.bagus.moviecataloguev4.model.movie

import android.os.Parcelable
import com.bagus.moviecataloguev4.model.movie.MovieGenres
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieGenresResponse(var genres : ArrayList<MovieGenres>): Parcelable