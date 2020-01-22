package com.bagus.moviecataloguev4.ui.movies


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.bagus.moviecataloguev4.api.Client
import com.bagus.moviecataloguev4.api.Route
import com.bagus.moviecataloguev4.model.movie.MovieGenresResponse

import com.bagus.moviecataloguev4.model.movie.MovieResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bagus.moviecataloguev4.model.movie.MovieGenres as MC

class MovieViewModel : ViewModel() {

    var listMovieCategorys = MutableLiveData<ArrayList<MC>>()
    var g : ArrayList<MC> = ArrayList<MC>()
    var index : Int = 0
    var dataMovie : ArrayList<MC> = ArrayList<MC>()


    internal fun getCategorys(): LiveData<ArrayList<MC>> {

        return listMovieCategorys
    }


    fun getGenre(){
        index = 0
        dataMovie.clear()
        val Client = Client()
        val apiRoute: Route = Client.getClient()!!.create(Route::class.java)
        apiRoute.getCategoryMovie().enqueue(object :
            Callback<MovieGenresResponse> {
            override fun onResponse(call: Call<MovieGenresResponse>, response: Response<MovieGenresResponse>) {

                if(response.code() == 200) {
                    g = response.body().genres
                    getMovie()
                }

                Log.d("RESPONSE",response.raw().request().url().toString())
            }
            override fun onFailure(call: Call<MovieGenresResponse>, t: Throwable){
                println(t)

            }
        })
    }

    fun getMovie(){
        val Client = Client()
        val apiRoute: Route = Client.getClient()!!.create(Route::class.java)
        apiRoute.getDataMovie(g[index].id).enqueue(object :
            Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.code() == 200 ) {
                    val items = MC(g[index].id,g[index].name,response.body().results)
                    index++
                    dataMovie.add(items)
                    if((index) < g.count()){
                        getMovie()
                    }else {
                        listMovieCategorys.postValue(dataMovie)
                    }
                }

                Log.d("RESPONSE",response.raw().request().url().toString())
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable){
                Log.e("Ops",t.message)
                Log.d("RESPONSE",g[index].id.toString())
            }
        })
    }




}