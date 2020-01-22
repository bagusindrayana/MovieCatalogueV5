package com.bagus.moviecataloguev4.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bagus.moviecataloguev4.DetailItem
import com.bagus.moviecataloguev4.R
import com.bagus.moviecataloguev4.db.AppDatabase
import com.bagus.moviecataloguev4.db.table.Favorite
import com.bagus.moviecataloguev4.model.movie.Movie
import com.bagus.moviecataloguev4.model.tv.Tv
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.favorite_cardview.view.*


class FavoriteAdapter( private var listFavorites : List<Favorite>) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_cardview, parent, false)
        return FavoriteViewHolder(view)
    }
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorites[position])

    }

    override fun getItemCount(): Int = this.listFavorites.size

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fav: Favorite) {
            with(itemView){
                title_fav.text = fav.title
                val options = RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)

                Glide.with(context!!).load("https://image.tmdb.org/t/p/w342/"+fav.poster).apply(options).into(itemView.coverImageView)
                itemView.setOnClickListener {
                    val moveWithDataIntent = Intent(context, DetailItem::class.java)

                    if(fav.type == "MOVIE"){
                        var data = Movie(fav.item_id,fav.title,fav.poster)
                        moveWithDataIntent.putExtra(DetailItem.EXTRA_CLASS, "MOVIE")
                        moveWithDataIntent.putExtra(DetailItem.EXTRA_DATA, data)
                    }

                    if(fav.type == "TV"){
                        var data = Tv(fav.item_id,fav.title,fav.poster)
                        moveWithDataIntent.putExtra(DetailItem.EXTRA_CLASS, "TV")
                        moveWithDataIntent.putExtra(DetailItem.EXTRA_DATA, data)
                    }

                    context?.startActivity(moveWithDataIntent)

                }
                itemView.delete_button.setOnClickListener{
                    var rom : AppDatabase? = null;
                    context?.let {
                        rom = Room.databaseBuilder(it, AppDatabase::class.java,"favorites").allowMainThreadQueries().build()
                    }
                    rom?.favoriteDao()?.delete(fav)

                    if(fav.type == "MOVIE"){
                        rom?.favoriteDao()?.getMovie()?.let{
                            listFavorites = it
                            notifyItemRemoved(getAdapterPosition())
                            notifyItemRangeChanged(getAdapterPosition(), it.count())

                        }
                    }

                    if(fav.type == "TV"){
                        rom?.favoriteDao()?.getTv()?.let{
                            listFavorites = it
                            notifyItemRemoved(getAdapterPosition())
                            notifyItemRangeChanged(getAdapterPosition(), it.count())

                        }
                    }



                }


            }
        }
    }


}