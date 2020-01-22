package com.bagus.moviecataloguev4

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.room.Room
import com.bagus.moviecataloguev4.api.Client
import com.bagus.moviecataloguev4.api.Route
import com.bagus.moviecataloguev4.db.AppDatabase
import com.bagus.moviecataloguev4.db.table.Favorite
import com.bagus.moviecataloguev4.model.movie.Movie
import com.bagus.moviecataloguev4.model.movie.DetailMovie
import com.bagus.moviecataloguev4.model.tv.DetailTv
import com.bagus.moviecataloguev4.model.tv.Tv
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailItem : AppCompatActivity() {
    private var mTopToolbar: Toolbar? = null
    private var menu : Menu? = null
    var rom : AppDatabase? = null
    var datanya : Array<Any> = arrayOf()

    var mProgressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_item)

        rom = Room.databaseBuilder(this, AppDatabase::class.java,"favorites").allowMainThreadQueries().build()

        mTopToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);


        mTopToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

        mProgressBar = findViewById(R.id.loading)





        val kelas = intent.getStringExtra(EXTRA_CLASS)
        if (kelas == "MOVIE") {
            val selectedData = intent.getParcelableExtra<Movie>(EXTRA_DATA)
            getDetailMovie(selectedData.id)
            setTitle(selectedData.title)



            datanya =  arrayOf(0,selectedData.title,selectedData.poster_path,selectedData.id,"MOVIE")
        } else if (kelas == "TV") {
            val selectedData = intent.getParcelableExtra<Tv>(EXTRA_DATA)
            getDetailTv(selectedData.id)
            setTitle(selectedData.name)
            datanya = arrayOf(0,selectedData.name,selectedData.poster_path,selectedData.id,"TV")
        }






    }
    
    fun getDetailMovie(movie_id : Int){
        showLoading(true)
        val Client = Client()
        val apiRoute: Route = Client.getClient()!!.create(Route::class.java)
        apiRoute.getDetailMovie(movie_id).enqueue(object :
            Callback<DetailMovie> {
            override fun onResponse(call: Call<DetailMovie>, response: Response<DetailMovie>) {

                if(response.code() == 200) {
                    val title = findViewById<TextView>(R.id.title_fav)
                    val desc = findViewById<TextView>(R.id.description)
                    val poster = findViewById<ImageView>(R.id.coverImageView)
                    val rating = findViewById<TextView>(R.id.rating)

                    title.text = response.body().title
                    desc.text = response.body().overview
                    rating.text = "Rating : "+response.body().vote_average.toString()
                    findViewById<TextView>(R.id.date).text = response.body().release_date
                    findViewById<TextView>(R.id.status).text = response.body().status
                    val options = RequestOptions()
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round)
                    Glide.with(applicationContext).load("https://image.tmdb.org/t/p/w780/"+response.body().backdrop_path).apply(options).into(poster)
                }

                //Log.d("RESPONSE",response.raw().request().url().toString())
                showLoading(false)
            }
            override fun onFailure(call: Call<DetailMovie>, t: Throwable){
                Log.e("Ops",t.message)
                showLoading(false)

            }
        })
    }


    fun getDetailTv(tv_id : Int){
        showLoading(true)
        val Client = Client()
        val apiRoute: Route = Client.getClient()!!.create(Route::class.java)
        apiRoute.getDetailTv(tv_id).enqueue(object :
            Callback<DetailTv> {
            override fun onResponse(call: Call<DetailTv>, response: Response<DetailTv>) {

                if(response.code() == 200) {
                    val title = findViewById<TextView>(R.id.title_fav)
                    val desc = findViewById<TextView>(R.id.description)
                    val poster = findViewById<ImageView>(R.id.coverImageView)
                    val rating = findViewById<TextView>(R.id.rating)

                    title.text = response.body().name
                    desc.text = response.body().overview
                    rating.text = "Rating : "+response.body().vote_average.toString()

                    findViewById<TextView>(R.id.date).text = response.body().first_air_date
                    findViewById<TextView>(R.id.status).text = response.body().status
                    val options = RequestOptions()
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round)
                    Glide.with(applicationContext).load("https://image.tmdb.org/t/p/w780/"+response.body().backdrop_path).apply(options).into(poster)
                }

                Log.d("RESPONSE",response.raw().request().url().toString())
                showLoading(false)
            }
            override fun onFailure(call: Call<DetailTv>, t: Throwable){
                Log.e("Ops",t.message)
                showLoading(false)

            }


        })
    }

    companion object {
        val EXTRA_DATA = "data-item"
        val EXTRA_CLASS = "MOVIE"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail_item, menu)
        this.menu = menu
        val cek = rom?.favoriteDao()?.findByItemId(datanya[3].toString().toInt(),datanya[4].toString())
        println(cek)
        if(cek != null){
            menu?.getItem(0)?.setIcon(R.drawable.ic_favorite_red)
        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            var text : String

            val cek = rom?.favoriteDao()?.findByItemId(datanya[3].toString().toInt(),datanya[4].toString())
            if(cek == null){
                rom?.favoriteDao()?.insertAll(Favorite(datanya[0].toString().toInt(),datanya[1].toString(),datanya[2].toString(),datanya[3].toString().toInt(),datanya[4].toString()))
                text = "Add "+datanya[1].toString()+" To Favorite"
            } else {
                text = datanya[1].toString()+" Already In Favorite"
            }

            menu?.getItem(0)?.setIcon(R.drawable.ic_favorite_red)

            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            this.findViewById<FrameLayout>(R.id.poster).visibility = View.GONE
            this.findViewById<TextView>(R.id.description).visibility = View.GONE
            this.findViewById<TableLayout>(R.id.tableLayout).visibility = View.GONE
            mProgressBar?.visibility  = View.VISIBLE
        } else {
            this.findViewById<FrameLayout>(R.id.poster).visibility = View.VISIBLE
            this.findViewById<TextView>(R.id.description).visibility = View.VISIBLE
            this.findViewById<TableLayout>(R.id.tableLayout).visibility = View.VISIBLE
            mProgressBar?.visibility = View.GONE
        }
    }
}
