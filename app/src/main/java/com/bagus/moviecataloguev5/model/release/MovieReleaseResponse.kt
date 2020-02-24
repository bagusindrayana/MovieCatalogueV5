package com.bagus.moviecataloguev5.model.release

import android.os.Parcelable
import com.bagus.moviecataloguev5.model.movie.Movie
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieReleaseResponse(
    var page : String,
    var total_result : String,
    var total_pages:String,
    var results : ArrayList<Movie>
) : Parcelable