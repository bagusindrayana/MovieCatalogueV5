package com.bagus.moviecataloguev4.ui.movies.category


import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bagus.moviecataloguev4.R
import com.bagus.moviecataloguev4.model.movie.MovieGenres
import com.bagus.moviecataloguev4.ui.movies.item.CardViewMoviesAdapter
import kotlinx.android.synthetic.main.category_cardview.view.*



class CardViewMovieCategorysAdapter(private val listMovieGenres: ArrayList<MovieGenres>) : RecyclerView.Adapter<CardViewMovieCategorysAdapter.CardViewViewHolder>() {

    private var context: Context? = null
    private  var rvItems:RecyclerView? = null
    private val viewPool = RecyclerView.RecycledViewPool()


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardViewViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.category_cardview, viewGroup, false)
        context = viewGroup.context
        rvItems = view.findViewById(R.id.item_list)
        return CardViewViewHolder(view)
    }

    override fun onBindViewHolder(cardViewViewHolder: CardViewViewHolder, i: Int) {

        val cat = listMovieGenres[i]
        if(cat.list_s != null){
            cardViewViewHolder.name.text = cat.name
            cardViewViewHolder.recyclerView.apply {
                layoutManager = LinearLayoutManager(cardViewViewHolder.recyclerView.context, RecyclerView.HORIZONTAL, false)
                adapter = CardViewMoviesAdapter(cat.list_s)
                setRecycledViewPool(viewPool)
            }
        }
    }

    override fun getItemCount(): Int {
        return listMovieGenres.size
    }

    inner class CardViewViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recyclerView : RecyclerView = itemView.item_list
        internal var name: TextView
        init {
           name = itemView.findViewById(R.id.category_name)

        }

    }



}

