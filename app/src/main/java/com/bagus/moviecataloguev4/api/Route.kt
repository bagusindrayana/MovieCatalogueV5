package com.bagus.moviecataloguev4.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.bagus.moviecataloguev4.model.movie.MovieGenresResponse
import com.bagus.moviecataloguev4.model.movie.MovieResponse
import com.bagus.moviecataloguev4.model.tv.TvGenresResponse
import com.bagus.moviecataloguev4.model.tv.TvResponse
import retrofit2.Call


interface Route {
    companion object {

        private const val API_KEY = "3eb2eb88bafdb0af014d82b0657dc289"
    }
    @GET("genre/movie/list?api_key="+API_KEY)
    fun getCategoryMovie(): Call<MovieGenresResponse>

    @GET("discover/movie?api_key="+API_KEY)
    fun getDataMovie(@Query("with_genres") with_genres: Int?):Call<MovieResponse>

    @GET("genre/tv/list?api_key="+API_KEY)
    fun getCategoryTv(): Call<TvGenresResponse>

    @GET("discover/tv?api_key="+API_KEY)
    fun getDataTv(@Query("with_genres") with_genres: Int?):Call<TvResponse>
}