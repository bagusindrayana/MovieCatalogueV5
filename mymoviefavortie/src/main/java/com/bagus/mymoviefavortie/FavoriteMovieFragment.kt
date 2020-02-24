package com.bagus.mymoviefavorite.ui.favorite.movie

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bagus.mymoviefavorite.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.bagus.mymoviefavortie.Favorite
import com.bagus.mymoviefavortie.FavoriteAdapter
import com.bagus.mymoviefavortie.MappingHelper
import com.bagus.mymoviefavortie.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class FavoriteMovieFragment : Fragment() {
    private var rv_favorites: RecyclerView? = null

    private lateinit var adapter: FavoriteAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favortie_movie, container, false)
        super.onViewCreated(view, savedInstanceState)
        rv_favorites = view.findViewById(R.id.rv_favorites)
        rv_favorites?.layoutManager = LinearLayoutManager(this.activity)
        rv_favorites?.setHasFixedSize(true)

        adapter = FavoriteAdapter(ArrayList())
        rv_favorites?.adapter = adapter
        
//        var rom : AppDatabase? = null;
//        context?.let {
//            rom = Room.databaseBuilder(it, AppDatabase::class.java,"favorites").allowMainThreadQueries().build()
//        }
//        val data = rom?.favoriteDao()?.getMovie()
//        data?.let{
//            rv_favorites!!.adapter =
//                FavoriteAdapter(
//                    it
//                )
//        }
//        rom?.close()


        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadFavoritesAsync()
            }
        }

        context?.contentResolver?.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadFavoritesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Favorite>(EXTRA_STATE)
            if (list != null) {
                adapter = FavoriteAdapter(list)
            }
        }
        
        return view



    }

    private fun loadFavoritesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            //progressbar.visibility = View.VISIBLE
            val deferredFavorites = async(Dispatchers.IO) {
                val cursor = context?.contentResolver?.query(CONTENT_URI, null, "MOVIE", null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favorites = deferredFavorites.await()
            //progressbar.visibility = View.INVISIBLE
            if (favorites.size > 0) {
                adapter = FavoriteAdapter(favorites)
                rv_favorites?.adapter = adapter
            } else {
                adapter = FavoriteAdapter(ArrayList())
                rv_favorites?.adapter = adapter
                Log.e("ERROR","Tidak ada data saat ini")
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getItem())
    }


}