package com.bagus.moviecataloguev5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bagus.moviecataloguev5.ui.search.SearchTvViewModel
import com.bagus.tvcataloguev5.ui.search.SearchTvAdapter


class SearchTvActivity : AppCompatActivity() {

    lateinit var searchViewMovdel: SearchTvViewModel

    private var rvTvs: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)

        rvTvs = findViewById(R.id.rv_movies)
        rvTvs?.setHasFixedSize(true)
        rvTvs?.layoutManager = LinearLayoutManager(this)

        searchViewMovdel = ViewModelProvider(this).get(SearchTvViewModel::class.java)
        val cari = intent.getStringExtra(QUERY)
        setTitle("Search Tv "+cari)
        if(::searchViewMovdel.isInitialized){
            searchViewMovdel.searchTv(cari).observe(this, Observer { items ->
                if(items == "error"){
                    Toast.makeText(this@SearchTvActivity, "Ops Ada Masalah Saat Meload Data!", Toast.LENGTH_SHORT).show()
                }
            })

            searchViewMovdel.getListSearch().observe(this, Observer { items ->
                if (items != null) {
                    rvTvs!!.adapter =
                        SearchTvAdapter(
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
