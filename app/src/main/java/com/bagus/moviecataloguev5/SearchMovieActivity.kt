package com.bagus.moviecataloguev5


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bagus.moviecataloguev5.ui.search.SearchMovieAdapter
import com.bagus.moviecataloguev5.ui.search.SearchMoviewViewModel

class SearchMovieActivity : AppCompatActivity() {
    lateinit var searchViewMovdel: SearchMoviewViewModel

    private var rvMovies: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)

        rvMovies = findViewById(R.id.rv_movies)
        rvMovies?.setHasFixedSize(true)
        rvMovies?.layoutManager = LinearLayoutManager(this)

        searchViewMovdel = ViewModelProvider(this).get(SearchMoviewViewModel::class.java)
        val cari = intent.getStringExtra(QUERY)
        setTitle("Search Movie "+cari)
        if(::searchViewMovdel.isInitialized){
            searchViewMovdel.searchMovie(cari).observe(this, Observer { items ->
                if(items == "error"){
                    Toast.makeText(this@SearchMovieActivity, "Ops Ada Masalah Saat Meload Data!", Toast.LENGTH_SHORT).show()
                }
            })

            searchViewMovdel.getListSearch().observe(this, Observer { items ->
                if (items != null) {
                    rvMovies!!.adapter =
                        SearchMovieAdapter(
                            items
                        )
                }
            })
        }


    }


    companion object {
        val QUERY = ""

    }
}
