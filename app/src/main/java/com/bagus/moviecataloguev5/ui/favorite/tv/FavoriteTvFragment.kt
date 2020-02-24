package com.bagus.moviecataloguev5.ui.favorite.tv

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
import androidx.room.Room
import com.bagus.moviecataloguev5.R
import com.bagus.moviecataloguev5.db.AppDatabase
import com.bagus.moviecataloguev5.db.DatabaseContract
import com.bagus.moviecataloguev5.helper.MappingHelper
import com.bagus.moviecataloguev5.model.favorite.Favorite
import com.bagus.moviecataloguev5.ui.favorite.FavoriteAdapter
import com.bagus.moviecataloguev5.ui.favorite.tv.FavoriteTvFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteTvFragment : Fragment() {
    private var rv_favorites: RecyclerView? = null



    private lateinit var adapter: FavoriteAdapter

    companion object {
        private const val EXTRA_STATE2 = "EXTRA_STATE2"
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_tv, container, false)
        super.onViewCreated(view, savedInstanceState)
        rv_favorites = view.findViewById(R.id.rv_favorites)
        rv_favorites?.layoutManager = LinearLayoutManager(this.activity)
        rv_favorites?.setHasFixedSize(true)
//        var rom : AppDatabase? = null;
//        context?.let {
//            rom = Room.databaseBuilder(it, AppDatabase::class.java,"favorites").allowMainThreadQueries().build()
//        }
//        val data = rom?.favoriteDao()?.getTv()
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

        context?.contentResolver?.registerContentObserver(DatabaseContract.FavoriteColumns.CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadFavoritesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Favorite>(FavoriteTvFragment.EXTRA_STATE2)
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
                val cursor = context?.contentResolver?.query(DatabaseContract.FavoriteColumns.CONTENT_URI, null, "TV", null, null)
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
        outState.putParcelableArrayList(FavoriteTvFragment.EXTRA_STATE2, adapter.getItem())
    }
}
