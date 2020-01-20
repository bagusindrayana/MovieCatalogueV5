package com.bagus.moviecataloguev4.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.*
import com.bagus.moviecataloguev4.R
import com.bagus.moviecataloguev4.db.AppDatabase


class FavoriteFragment : Fragment() {

    private var rv_favorites: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        super.onViewCreated(view, savedInstanceState)

        getActivity()?.setTitle(R.string.title_favorite);
        rv_favorites = view.findViewById(R.id.rv_favorites)
        rv_favorites?.layoutManager = LinearLayoutManager(this.activity)
        rv_favorites?.setHasFixedSize(true)

        var rom : AppDatabase? = null;
        context?.let {
            rom = Room.databaseBuilder(it, AppDatabase::class.java,"favorites").allowMainThreadQueries().build()
        }

        val data = rom?.favoriteDao()?.getAll()


        data?.let{
            rv_favorites!!.adapter =
                FavoriteAdapter(
                    it
                )
        }

        return view
    }



}