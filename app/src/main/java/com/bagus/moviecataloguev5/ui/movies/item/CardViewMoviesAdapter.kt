package com.bagus.moviecataloguev5.ui.movies.item

import android.content.Context
import android.content.Intent
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bagus.moviecataloguev5.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import androidx.recyclerview.widget.RecyclerView
import com.bagus.moviecataloguev5.DetailItem
import com.bagus.moviecataloguev5.model.movie.Movie


class CardViewMoviesAdapter(private val listMovie: ArrayList<Movie>) : RecyclerView.Adapter<CardViewMoviesAdapter.CardViewViewHolder>() {
    private var context: Context? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardViewViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cardview, viewGroup, false)
        context = viewGroup.context
        return CardViewViewHolder(view)
    }

    override fun onBindViewHolder(cardViewViewHolder: CardViewViewHolder, i: Int) {
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

                Glide.with(cardViewViewHolder.poster.getContext()).load("https://image.tmdb.org/t/p/w342/"+movie.poster_path).apply(options).into(cardViewViewHolder.poster)
            } else {
                Log.d("PosterLog1", movie.poster_path.toString())
                cardViewViewHolder.poster.setImageResource(R.mipmap.ic_launcher_round)
            }
        }  catch (e: Exception) {
            Log.d("PosterLog2", e.message.toString())
            cardViewViewHolder.poster.setImageResource(R.mipmap.ic_launcher_round)
        }
        cardViewViewHolder.title.text = movie.title
        cardViewViewHolder.itemView.setOnClickListener {
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

    inner class CardViewViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var poster: ImageView
        internal var title: TextView
        init {
            poster = itemView.findViewById(R.id.coverImageView)
            title = itemView.findViewById(R.id.title_fav)
        }
    }


}