package com.bagus.moviecataloguev4
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.bagus.moviecataloguev4.db.AppDatabase
import com.bagus.moviecataloguev4.db.table.Favorite
import com.bagus.moviecataloguev4.model.movie.Movie
import com.bagus.moviecataloguev4.model.tv.Tv
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_item.*


class DetailItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_item)
        var datanya : Array<Any> = arrayOf()
        val title = findViewById<TextView>(R.id.title_fav)
        val desc = findViewById<TextView>(R.id.description)
        val poster = findViewById<ImageView>(R.id.coverImageView)
        val rating = findViewById<TextView>(R.id.rating)


        val kelas = intent.getStringExtra(EXTRA_CLASS)
        if (kelas == "MOVIE") {
            val selectedData = intent.getParcelableExtra<Movie>(EXTRA_DATA)

            title.text = selectedData.title
            desc.text = selectedData.overview
            rating.text = "Rating : "+selectedData.vote_average.toString()
            val options = RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
            Glide.with(this!!).load("https://image.tmdb.org/t/p/w780/"+selectedData.backdrop_path).apply(options).into(poster)


            datanya =  arrayOf(0,selectedData.title,selectedData.poster_path,selectedData.id,"MOVIE")
        } else if (kelas == "TV") {
            val selectedData = intent.getParcelableExtra<Tv>(EXTRA_DATA)

            title.text = selectedData.name
            desc.text = selectedData.overview
            rating.text = "Rating : "+selectedData.vote_average.toString()
            val options = RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
            Glide.with(this!!).load("https://image.tmdb.org/t/p/w780/"+selectedData.backdrop_path).apply(options).into(poster)
            datanya = arrayOf(0,selectedData.name,selectedData.poster_path,selectedData.id,"TV")
        }


        fav_button.setOnClickListener {

            val rom = Room.databaseBuilder(this, AppDatabase::class.java,"favorites").allowMainThreadQueries().build()
            rom?.favoriteDao()?.insertAll(Favorite(datanya[0].toString().toInt(),datanya[1].toString(),datanya[2].toString(),datanya[3].toString().toInt(),datanya[4].toString()))

            val text : String = "Add "+datanya[1].toString()+" To Favorite"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
            fav_button.setBackgroundTintList(this.getResources().getColorStateList( R.color.colorPrimary))
        }
    }

    companion object {
        val EXTRA_DATA = "data-item"
        val EXTRA_CLASS = "MOVIE"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}
