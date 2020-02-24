package com.bagus.moviecataloguev5.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagus.moviecataloguev5.api.Client
import com.bagus.moviecataloguev5.api.Route
import com.bagus.moviecataloguev5.model.tv.Tv
import com.bagus.moviecataloguev5.model.search.SearchTvResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchTvViewModel : ViewModel() {
    var listSearch = MutableLiveData<ArrayList<Tv>>()
    var responseMessage : MutableLiveData<String> = MutableLiveData<String>()
    fun getListSearch(): LiveData<ArrayList<Tv>>{
        return listSearch
    }

    fun searchTv(query : String) : LiveData<String>{

        responseMessage.postValue("loading")
        val Client = Client()
        val apiRoute: Route = Client.getClient()!!.create(Route::class.java)
        apiRoute.getSearchTv(query).enqueue(object :
            Callback<SearchTvResponse> {
            override fun onResponse(call: Call<SearchTvResponse>, response: Response<SearchTvResponse>) {

                if(response.code() == 200) {
                    listSearch.postValue(response.body().results)
                    responseMessage.postValue("berhasil")
                } else {
                    responseMessage.postValue("error")
                }
            }
            override fun onFailure(call: Call<SearchTvResponse>, t: Throwable){
                Log.e("Ops", t.message)
                responseMessage.postValue("error")

            }
        })

        return responseMessage
    }

}