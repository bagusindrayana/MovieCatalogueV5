package com.bagus.moviecataloguev5.ui.search

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bagus.moviecataloguev5.DetailItem
import com.bagus.moviecataloguev5.R
import com.bagus.moviecataloguev5.model.movie.Movie
import com.bagus.moviecataloguev5.ui.movies.item.CardViewMoviesAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class SearchMovieAdapter(private val listMovie: ArrayList<Movie>) : RecyclerView.Adapter<SearchMovieAdapter.ItemSearchHolder>() {
    private var context: Context? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ItemSearchHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_search, viewGroup, false)
        context = viewGroup.context
        return ItemSearchHolder(view)
    }

    override fun onBindViewHolder(holder: ItemSearchHolder, i: Int) {
        val movie = listMovie[i]
        val options = RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)


        try {
            if (movie.poster_path != null && movie.poster_path != ""){
                val options = RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)

                Glide.with(holder.poster.getContext()).load("https://image.tmdb.org/t/p/w342/"+movie.poster_path).apply(options).into(holder.poster)
                holder.poster.contentDescription = "Poster "+movie.title
            } else {

                holder.poster.setImageResource(R.mipmap.ic_launcher_round)
            }
        }  catch (e: Exception) {

            holder.poster.setImageResource(R.mipmap.ic_launcher_round)
        }
        holder.title.text = movie.title
        holder.rating.text = movie.vote_average
        holder.release.text = movie.release_date
        holder.itemView.setOnClickListener {
            if(context != null){
                val moveWithDataIntent = Intent(context, DetailItem::class.java)
                moveWithDataIntent.putExtra(DetailItem.EXTRA_CLASS, "MOVIE")
                moveWithDataIntent.putExtra(DetailItem.EXTRA_DATA, listMovie[i])
                context!!.startActivity(moveWithDataIntent)
            } else {
                context = it.getContext()
            }

        }

    }


    override fun getItemCount(): Int {
        return listMovie.size
    }

    inner class ItemSearchHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var poster: ImageView
        internal var title: TextView
        internal var rating : TextView
        internal var release : TextView
        init {
            poster = itemView.findViewById(R.id.poster)
            title = itemView.findViewById(R.id.title)
            rating = itemView.findViewById(R.id.rating)
            release = itemView.findViewById(R.id.release_date)
        }
    }


}