package com.bagus.moviecataloguev5

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.room.Room
import com.bagus.moviecataloguev5.api.Client
import com.bagus.moviecataloguev5.api.Route
import com.bagus.moviecataloguev5.db.AppDatabase
import com.bagus.moviecataloguev5.db.DatabaseContract
import com.bagus.moviecataloguev5.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.bagus.moviecataloguev5.db.FavoriteHelper
import com.bagus.moviecataloguev5.helper.MappingHelper
import com.bagus.moviecataloguev5.model.movie.DetailMovie
import com.bagus.moviecataloguev5.model.movie.Movie
import com.bagus.moviecataloguev5.model.tv.DetailTv
import com.bagus.moviecataloguev5.model.tv.Tv
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class DetailItem : AppCompatActivity() {
    private var mTopToolbar: Toolbar? = null
    private var menu : Menu? = null
    var rom : AppDatabase? = null
    var datanya : Array<String> = arrayOf()

    var mProgressBar: ProgressBar? = null

    private lateinit var favoriteHelper: FavoriteHelper

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
            tampilkanFilm()
        } else if (kelas == "TV") {
            tampilkanTv()
        }

        favoriteHelper = FavoriteHelper.getInstance(this)



    }

    fun tampilkanTv(){
        val selectedData = intent.getParcelableExtra<Tv>(EXTRA_DATA)
        getDetailTv(selectedData.id)
        setTitle(selectedData.name)
        datanya = arrayOf(
            "0",
            selectedData.name,
            selectedData.poster_path,
            selectedData.id.toString(),
            "TV"
        )
    }

    fun tampilkanFilm(){
        val selectedData = intent.getParcelableExtra<Movie>(EXTRA_DATA)
        getDetailMovie(selectedData.id)
        setTitle(selectedData.title)
        datanya = arrayOf(
            "0",
            selectedData.title,
            selectedData.poster_path,
            selectedData.id.toString(),
            "MOVIE"
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArray("DATANYA", datanya)
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
        if(datanya.size > 0){
            val uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + datanya[3].toString())

            val get = contentResolver?.query(uriWithId,null,null,null,null)
            Log.i("CURSOR",get?.getCount().toString())
            if(get != null && get.getCount() > 0){
                val cek = MappingHelper.mapCursorToArrayList(get)
                print(cek)
                if(cek.size > 0){
                    menu?.getItem(0)?.setIcon(R.drawable.ic_favorite_red)
                }

                get.close()

            }

            get?.close()
        }

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            var text : String

            val cek = rom?.favoriteDao()?.findByItemId(datanya[3].toString().toInt(),datanya[4].toString())
            if(cek == null){
                //rom?.favoriteDao()?.insertAll(Favorite(datanya[0].toString().toInt(),datanya[1].toString(),datanya[2].toString(),datanya[3].toString().toInt(),datanya[4].toString()))
                val values = ContentValues()

                values.put(DatabaseContract.FavoriteColumns.TITLE, datanya[1].toString())
                values.put(DatabaseContract.FavoriteColumns.POSTER, datanya[2].toString())
                values.put(DatabaseContract.FavoriteColumns.ITEM_ID,datanya[3].toString().toInt())
                values.put(DatabaseContract.FavoriteColumns.TYPE,datanya[4].toString())

                contentResolver.insert(CONTENT_URI,values)
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

            mProgressBar?.visibility  = View.VISIBLE
        } else {

            mProgressBar?.visibility = View.GONE
        }
    }
}
