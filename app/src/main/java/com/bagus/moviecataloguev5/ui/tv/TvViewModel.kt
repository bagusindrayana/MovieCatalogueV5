package com.bagus.moviecataloguev5.ui.tv

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagus.moviecataloguev5.api.Client
import com.bagus.moviecataloguev5.api.Route

import com.bagus.moviecataloguev5.model.tv.TvGenresResponse
import com.bagus.moviecataloguev5.model.tv.TvGenres
import com.bagus.moviecataloguev5.model.tv.TvResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvViewModel : ViewModel() {

    var listTvCategorys = MutableLiveData<ArrayList<TvGenres>>()

    var g : ArrayList<TvGenres> = ArrayList<TvGenres>()
    var index : Int = 0
    var dataTv : ArrayList<TvGenres> = ArrayList<TvGenres>()


    internal fun getCategorys(): LiveData<ArrayList<TvGenres>> {

        return listTvCategorys
    }

    fun getGenre(){
        index = 0
        dataTv.clear()
        val Client = Client()
        val apiRoute: Route = Client.getClient()!!.create(Route::class.java)
        apiRoute.getCategoryTv().enqueue(object :
            Callback<TvGenresResponse> {
            override fun onResponse(call: Call<TvGenresResponse>, response: Response<TvGenresResponse>) {

                if(response.code() == 200) {
                    g = response.body().genres
                    getTv()
                }

                Log.d("RESPONSE",response.raw().request().url().toString())
            }
            override fun onFailure(call: Call<TvGenresResponse>, t: Throwable){
                Log.e("Ops",t.message)

            }
        })
    }

    fun getTv(){
        val Client = Client()
        val apiRoute: Route = Client.getClient()!!.create(Route::class.java)
        apiRoute.getDataTv(g[index].id).enqueue(object :
            Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                if(response.code() == 200) {
                    val items = TvGenres(g[index].id,g[index].name,response.body().results)
                    index++
                    dataTv.add(items)
                    if((index) < g.count()){
                        getTv()
                    }else {
                        listTvCategorys.postValue(dataTv)
                    }
                }

                Log.d("RESPONSE",response.raw().request().url().toString())
            }
            override fun onFailure(call: Call<TvResponse>, t: Throwable){
                Log.e("Ops",t.message)
                Log.d("RESPONSE",g[index].id.toString())
            }
        })
    }




}