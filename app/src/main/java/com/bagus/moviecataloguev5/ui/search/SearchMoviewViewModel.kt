package com.bagus.moviecataloguev5.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagus.moviecataloguev5.api.Client
import com.bagus.moviecataloguev5.api.Route
import com.bagus.moviecataloguev5.model.movie.Movie
import com.bagus.moviecataloguev5.model.search.SearchMovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMoviewViewModel : ViewModel() {
    var listSearch = MutableLiveData<ArrayList<Movie>>()
    var responseMessage : MutableLiveData<String> = MutableLiveData<String>()
    fun getListSearch(): LiveData<ArrayList<Movie>>{
        return listSearch
    }

    fun searchMovie(query : String) : LiveData<String>{

        responseMessage.postValue("loading")
        val Client = Client()
        val apiRoute: Route = Client.getClient()!!.create(Route::class.java)
        apiRoute.getSearchMovie(query).enqueue(object :
            Callback<SearchMovieResponse> {
            override fun onResponse(call: Call<SearchMovieResponse>, response: Response<SearchMovieResponse>) {

                if(response.code() == 200) {
                    listSearch.postValue(response.body().results)
                    responseMessage.postValue("berhasil")
                } else {
                    responseMessage.postValue("error")
                }
            }
            override fun onFailure(call: Call<SearchMovieResponse>, t: Throwable){
                Log.e("Ops", t.message)
                responseMessage.postValue("error")

            }
        })

        return responseMessage
    }

}